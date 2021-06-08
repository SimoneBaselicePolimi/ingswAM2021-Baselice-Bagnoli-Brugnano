package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.cli.view.grid.LineBorderStyle;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.client.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.client.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.utils.Colour;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ProductionSelectionDashboardView extends AbstractPlayerDashboardView {

    protected List<ClientProductionRepresentation> alreadySelectedProductions;
    protected Map<ResourceType, Integer> resourcesLeftToThePlayer;

    protected Map<ResourceType, Integer> starResourcesCost = new HashMap<>();
    protected Map<ResourceType, Integer> starResourcesReward = new HashMap<>();

    protected GameView gameView;

    public ProductionSelectionDashboardView(
        List<ClientProductionRepresentation> alreadySelectedProductions,
        Map<ResourceType, Integer> resourcesLeftToThePlayer,
        CliClientManager clientManager,
        GameView gameView
    ) {
        super(clientManager.getMyPlayer(), clientManager, gameView);

        this.alreadySelectedProductions = alreadySelectedProductions;
        this.resourcesLeftToThePlayer = resourcesLeftToThePlayer;

        askPlayerForProduction();
    }


    CompletableFuture<Void> askPlayerForProduction(){

        return UserChoicesUtils.makeUserChoose(clientManager)
            .addUserChoice(
                this::askPlayerForTypeOfProductions,
                "client.cli.playerDashboard.activateNewProduction"
            )
            .addUserChoice(
                () -> {
                    CompletableFuture<Void> f = askPlayerForProduction();
                    f.exceptionally(e -> {
                        gameView.setMainContentView(new PlayerDashboardView(
                            activePlayer,
                            clientManager,
                            gameView
                        ));
                        return null;
                    });
                    f.thenCompose(input -> sendPlayerChoiceToServer());
                },
                    "client.cli.playerDashboard.endProductionsActivation"
            )
            .addUserChoice(
                () -> gameView.setMainContentView(new PlayerDashboardView(
                    activePlayer,
                    clientManager,
                    gameView
                )),
                "client.cli.playerDashboard.cancelSelectedProductions"
            ).apply();
    }

    CompletableFuture<Void> askPlayerForTypeOfProductions() {
        //TODO add return to menu option
        return UserChoicesUtils.makeUserChoose(clientManager)
            .addUserChoice(
                this::askPlayerForBaseProduction,
                "client.cli.playerDashboard.activateBaseProduction"
            )
            .addUserChoice(
                this::askPlayerForDashboardProductions,
                "client.cli.playerDashboard.activateDashboardProductions"
            )
//            .addUserChoice(
//                () -> askPlayerForLeaderCardsProduction(),
//                "client.cli.playerDashboard.activateLeaderCardsProduction"
//            )
            .addUserChoice(
                () -> gameView.setMainContentView(new ProductionSelectionLeaderCardView(
                    alreadySelectedProductions, resourcesLeftToThePlayer, clientManager, gameView
                )),
                "client.cli.playerDashboard.leaderCardList"
            ).apply();
    }

    CompletableFuture<Void> askPlayerForDashboardProductions () {

        return clientManager.askUserLocalized("client.cli.playerDashboard.askPlayerForDevProductionsChoice")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                if(intInput > 0 && intInput <= dashboardPlayerContext.getDevelopmentCardDecks().size()) {
                    ClientProductionRepresentation production = dashboardPlayerContext.getDevelopmentCardDecks()
                        .get(intInput - 1).peek().getProduction();
                    if(alreadySelectedProductions.contains(production)) {
                        clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionAlreadyChosen");
                        return askPlayerForTypeOfProductions();
                    }
                    return checkAndRemoveResourcesCost(production);
                }
                else {
                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionNumberIsInvalid");
                    return askPlayerForDashboardProductions();
                }
            });
    }

    CompletableFuture<Void> askPlayerForBaseProduction() {

        return clientManager.askUserLocalized("client.cli.playerDashboard.askPlayerForBaseProductionChoice")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                if(intInput > 0 && intInput <= dashboardPlayerContext.getBaseProductions().size()) {
                    ClientProductionRepresentation production = baseProductions.get(intInput-1);
                    if(alreadySelectedProductions.contains(production)) {
                        clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionAlreadyChosen");
                        return askPlayerForTypeOfProductions();
                    }
                    return checkAndRemoveResourcesCost(production);
                }
                else {
                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionNumberIsInvalid");
                    return askPlayerForBaseProduction();
                }
            });
    }

    CompletableFuture<Void> checkAndRemoveResourcesCost(ClientProductionRepresentation production) {
        if (checkIfThePlayerHasNecessaryResources(production)) {
            resourcesLeftToThePlayer = ResourceUtils.difference(resourcesLeftToThePlayer, production.getResourceCost());
            return askPlayerForStarResourcesCost(
                production,
                production.getStarResourceCost(),
                production.getStarResourceReward()
            );
        } else {
            clientManager.tellUserLocalized(
                "client.cli.playerDashboard.notifyPlayerHeDoesNotHaveNeededResources"
            );
            return CompletableFuture.failedFuture(new IllegalAccessException());
        }
    }

    protected boolean checkIfThePlayerHasNecessaryResources(ClientProductionRepresentation production) {
        int numOfStarResources = production.getStarResourceCost();
        int totalResourcesCost = production.getResourceCost().values().stream().reduce(0, Integer::sum);
        return (ResourceUtils.areResourcesAContainedInB(production.getResourceCost(), resourcesLeftToThePlayer))
            && (totalResourcesCost + numOfStarResources
            >= resourcesLeftToThePlayer.values().stream().reduce(0, Integer::sum));
    }

//    CompletableFuture<Void> askPlayerForLeaderCardsProduction() {
//        return clientManager.askUserLocalized("client.cli.playerDashboard.askPlayerForLeaderProductionsChoice")
//            .thenCompose(input -> {
//                int intInput = Integer.parseInt(input);
//                if (intInput > 0 && intInput < activePlayerContext.getActiveLeaderCardsProductions().size()) {
//                    ClientProductionRepresentation production = new ArrayList<ClientProductionRepresentation>(activePlayerContext.getActiveLeaderCardsProductions()).get(intInput-1);
//                    return checkIfThePlayerHasNecessaryResources(production);
//                } else {
//                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionNumberIsInvalid");
//                    return askPlayerForLeaderCardsProduction();
//                }
//            });
//    }

    CompletableFuture<Void> askPlayerForStarResourcesCost(
        ClientProductionRepresentation production,
        int numOfStarResourcesLeftToPay,
        int numOfStarResourcesLeftToObtain
    ) {

        if(numOfStarResourcesLeftToPay != 0){
            return clientManager.askUserLocalized("client.cli.playerDashboard.notifyPlayerNumberOfResourcesLeftToPay", numOfStarResourcesLeftToPay)
                .thenCompose(resources -> {

                    if(resources.matches("\\G\\d+\\s+\\w+\\s*$")) {
                        int numOfResources = Integer.parseInt(resources.split("\\s+")[0]);
                        ResourceType resourceType =
                            ResourceType.getResourceTypeFromLocalizedName(resources.split("\\s+")[1]);
                        if (
                            resourceType != null &&
                                numOfResources > 0 &&
                                numOfStarResourcesLeftToPay >= numOfResources &&
                                ResourceUtils.areResourcesAContainedInB(
                                    Map.of(resourceType, numOfResources),
                                    resourcesLeftToThePlayer
                                )
                        ) {
                            resourcesLeftToThePlayer = ResourceUtils.difference(
                                resourcesLeftToThePlayer,
                                Map.of(resourceType, numOfResources)
                            );
                            starResourcesCost.put(resourceType, numOfResources);
                            return askPlayerForStarResourcesCost(
                                production,
                                numOfStarResourcesLeftToPay - numOfResources,
                                numOfStarResourcesLeftToObtain
                            );
                        }

                    }

                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerResourcesAreInvalid");
                    return askPlayerForStarResourcesCost(
                        production,
                        numOfStarResourcesLeftToPay,
                        numOfStarResourcesLeftToObtain
                    );

                });
        }
        return askPlayerForStarResourceReward(production, numOfStarResourcesLeftToObtain);
    }

    CompletableFuture<Void> askPlayerForStarResourceReward(
        ClientProductionRepresentation production,
        int numOfStarResourcesLeftToObtain
    ) {

        if(numOfStarResourcesLeftToObtain != 0){
            return clientManager.askUserLocalized("client.cli.playerDashboard.notifyPlayerNumberOfResourcesLeftToObtain", numOfStarResourcesLeftToObtain)
                .thenCompose(resources -> {

                    if(resources.matches("\\G\\d+\\s+\\w+\\s*$")) {
                        int numOfResources = Integer.parseInt(resources.split("\\s+")[0]);
                        ResourceType resourceType = ResourceType.getResourceTypeFromLocalizedName(resources.split("\\s+")[1]);
                        if(resourceType != null && numOfResources > 0 && numOfStarResourcesLeftToObtain >= numOfResources) {
                            starResourcesReward.put(resourceType, numOfResources);
                            return askPlayerForStarResourceReward(
                                production,
                                numOfStarResourcesLeftToObtain - numOfResources
                            );
                        }
                    }

                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerResourcesAreInvalid");
                    return askPlayerForStarResourceReward(
                        production,
                        numOfStarResourcesLeftToObtain
                    );

                });
        }

        alreadySelectedProductions.add(production);
        return askPlayerForProduction();

    }

    CompletableFuture<Void> sendPlayerChoiceToServer(){
        return clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
            new ProductionActionClientRequest(
                activePlayer,
                new HashSet<>(alreadySelectedProductions),
                starResourcesCost,
                starResourcesReward
            )
        )).thenCompose(serverMessage ->
            ServerMessageUtils.ifMessageTypeCompute(
                serverMessage,
                GameUpdateServerMessage.class,
                message -> {
                    clientManager.setGameState(GameState.MY_PLAYER_TURN_AFTER_MAIN_ACTION);
                    clientManager.handleGameUpdates(message.gameUpdates);
                    return CompletableFuture.<Void>completedFuture(null);
                }
            ).elseIfMessageTypeCompute(
                InvalidRequestServerMessage.class,
                message -> {
                    clientManager.tellUser(message.errorMessage);
                    gameView.setMainContentView(new PlayerDashboardView(
                        activePlayer,
                        clientManager,
                        gameView
                    ));
                    return CompletableFuture.completedFuture(null);
                }
            ).elseCompute(message -> {
                gameView.setMainContentView(new PlayerDashboardView(
                    activePlayer,
                    clientManager,
                    gameView
                ));
                return CompletableFuture.completedFuture(null);
                }).apply()
        );
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {

        for (DevCardDashboardDeckView cardDeckView : devCardDashboardDeckViewList) {
            ClientProductionRepresentation production = cardDeckView.cardView.card.getProduction();
            if(alreadySelectedProductions.contains(production))
                cardDeckView.cardView.cardGrid.setBorderStyle(new LineBorderStyle(Colour.GREEN));
            else if (checkIfThePlayerHasNecessaryResources(production))
                cardDeckView.cardView.cardGrid.setBorderStyle(new LineBorderStyle(Colour.YELLOW));
            else
                cardDeckView.cardView.cardGrid.setBorderStyle(new LineBorderStyle(Colour.GREY));
        }

        for (int p = 0; p < baseProductions.size(); p++) {
            ClientProductionRepresentation productionRepresentation = baseProductions.get(p);
            GridView productionGrid = baseProductionGridLists.get(p);
            if(alreadySelectedProductions.contains(productionRepresentation))
                productionGrid.setBorderStyle(new LineBorderStyle(Colour.GREEN));
            else if (checkIfThePlayerHasNecessaryResources(productionRepresentation))
                productionGrid.setBorderStyle(new LineBorderStyle(Colour.YELLOW));
            else
                productionGrid.setBorderStyle(new LineBorderStyle(Colour.GREY));
        }

        return super.getContentAsFormattedCharsBuffer();
    }

}
