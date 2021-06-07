package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.client.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.client.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ProductionSelectionDashboardView extends AbstractPlayerDashboardView {

    Player activePlayer;
    ClientPlayerContextRepresentation activePlayerContext;

    private int numOfStarResourcesLeftToPay = 0;
    private int numOfStarResourcesLeftToObtain = 0;

    protected Set<ClientProductionRepresentation> productions = new HashSet<>();
    protected Map<ResourceType, Integer> starResourcesCost = new HashMap<>();
    protected Map<ResourceType, Integer> starResourcesReward = new HashMap<>();

    protected Map<ResourceType, Integer> resourcesThePlayerOwns = new HashMap<>();
    protected Map<ResourceType, Integer> resourcesLeft = new HashMap<>();

    protected GameView gameView;

    public ProductionSelectionDashboardView(
        List<ClientProductionRepresentation> alreadySelectedProductions,
        Map<ResourceType, Integer> resourcesLeftToThePlayer,
        CliClientManager clientManager,
        GameView gameView
    ) {
        super(clientManager.getMyPlayer(), clientManager, gameView);

        activePlayer = clientManager.getGameContextRepresentation().getActivePlayer();
        activePlayerContext = clientManager.getGameContextRepresentation().getPlayerContext(activePlayer);

        startProdSelectionDialog();
    }

    void startProdSelectionDialog() {

        resourcesThePlayerOwns = activePlayerContext.getTotalResourcesOwnedByThePlayer();
        resourcesLeft = resourcesThePlayerOwns;
        UserChoicesUtils.PossibleUserChoices choices = UserChoicesUtils.makeUserChoose(clientManager);
        if (clientManager.getGameState().equals(GameState.MY_PLAYER_TURN_BEFORE_MAIN_ACTION)) {
            choices.addUserChoiceLocalized(
                () -> {
                    CompletableFuture<Void> f = askPlayerForProduction();
                    f.exceptionally(e -> {
                        startProdSelectionDialog();
                        return null;
                    });
                    f.thenCompose(input -> sendPlayerChoiceToServer());
                },
                "client.cli.playerDashboard.activateProduction"
            );
            choices.addUserChoiceLocalized(
                () -> gameView.setMainContentView(new LeaderCardsInDashBoardView(clientManager, clientManager.getMyPlayer())),
                "client.cli.playerDashboard.leaderCardList"
            );
            choices.addUserChoiceLocalized(
                () -> gameView.setMainContentView(new MainMenuView(clientManager)),
                "client.cli.game.returnToMenu"
            );
        }

        if (clientManager.getGameState().equals(GameState.GAME_SETUP)) {
            choices.addUserChoiceLocalized(
                () -> gameView.setMainContentView(new LeaderCardSetupView(clientManager, gameView)),
                "client.cli.game.returnToSetupView"
            );

        } else {
            choices.addUserChoiceLocalized(
                () -> gameView.setMainContentView(new LeaderCardsInDashBoardView(clientManager, clientManager.getMyPlayer())),
                "client.cli.playerDashboard.leaderCardList"
            );
            choices.addUserChoice(
                () -> gameView.setMainContentView(new MainMenuView(clientManager)),
                "client.cli.game.returnToMenu"
            );
        }

        choices.apply();
    }


    CompletableFuture<Void> askPlayerForProduction(){
        return UserChoicesUtils.makeUserChoose(clientManager)
            .addUserChoice(
                () -> askPlayerForTypeOfProductions(),
                "client.cli.playerDashboard.activateNewProduction"
            )
            .addUserChoice(
                () -> CompletableFuture.completedFuture(null),
                    "client.cli.playerDashboard.endProductionsActivation"
            )
            .addUserChoice(
                () -> {
                    productions.clear();
                    starResourcesCost.clear();
                    starResourcesReward.clear();
                    startProdSelectionDialog();
                    },
                "client.cli.playerDashboard.cancelSelectedProductions"
            ).apply();
    }

    CompletableFuture<Void> askPlayerForTypeOfProductions() {
        return UserChoicesUtils.makeUserChoose(clientManager)
            .addUserChoice(
                () ->  askPlayerForBaseProduction(),
                "client.cli.playerDashboard.activateBaseProduction"
            )
            .addUserChoice(
                () -> askPlayerForDashboardProductions(),
                "client.cli.playerDashboard.activateDashboardProductions"
            )
//            .addUserChoice(
//                () -> askPlayerForLeaderCardsProduction(),
//                "client.cli.playerDashboard.activateLeaderCardsProduction"
//            )
            .addUserChoice(
                () -> gameView.setMainContentView(new LeaderCardsInDashBoardView(clientManager, clientManager.getMyPlayer())),
                "client.cli.playerDashboard.leaderCardList"
            ).apply();
    }

    CompletableFuture<Void> askPlayerForDashboardProductions () {
        return clientManager.askUserLocalized("client.cli.playerDashboard.askPlayerForDevProductionsChoice")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                if (intInput > 0 && intInput <= activePlayerContext.getDevelopmentCardDecks().size()) {
                    ClientProductionRepresentation production = activePlayerContext.getDevelopmentCardDecks().get(intInput - 1).peek().getProduction();
                    return checkIfThePlayerHasNecessaryResources(production);
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
                if(intInput > 0 && intInput<activePlayerContext.getBaseProductions().size()) {
                    ClientProductionRepresentation production = new ArrayList<ClientProductionRepresentation>(activePlayerContext.getBaseProductions()).get(intInput-1);
                    return checkIfThePlayerHasNecessaryResources(production);
                }
                else {
                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionNumberIsInvalid");
                    return askPlayerForBaseProduction();
                }
            });
    }

    CompletableFuture<Void> checkIfThePlayerHasNecessaryResources(ClientProductionRepresentation production) {

        int totalResourcesCost = production.getResourceCost().values().stream().reduce(0, Integer::sum);
        if ((ResourceUtils.areResourcesAContainedInB(
            production.getResourceCost(),
            resourcesLeft)
        ) && totalResourcesCost >= resourcesLeft.values().stream().reduce(0, Integer::sum)) {
            productions.add(production);
            resourcesLeft = ResourceUtils.difference(resourcesLeft, production.getResourceCost());
            numOfStarResourcesLeftToObtain = production.getStarResourceReward();
            numOfStarResourcesLeftToPay = production.getStarResourceCost();
            return askPlayerForStarResourcesCost();
        } else {
            clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionNumberIsInvalid");
            return CompletableFuture.failedFuture(new IllegalAccessException());
        }

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

    CompletableFuture<Void> askPlayerForStarResourcesCost() {
        if(numOfStarResourcesLeftToPay != 0){
            return clientManager.askUserLocalized("client.cli.playerDashboard.notifyPlayerNumberOfResourcesLeftToPay", numOfStarResourcesLeftToPay)
                .thenCompose(resources -> {
                    int numOfResourceTypeCost = Integer.parseInt(resources.substring(0, 1));
                    ResourceType resourceTypeCost = ResourceType.getResourceTypeFromLocalizedName(resources.substring(2));
                    if(resourceTypeCost != null && numOfResourceTypeCost > 0 && numOfStarResourcesLeftToPay >= numOfResourceTypeCost) {
                        numOfStarResourcesLeftToPay -= numOfResourceTypeCost;
                        starResourcesCost.put(resourceTypeCost, numOfResourceTypeCost);
                        return askPlayerForStarResourcesCost();
                    }

                    else {
                        clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerResourcesAreInvalid");
                        return askPlayerForStarResourcesCost();
                    }
                });
        }
        return askPlayerForStarResourceReward();
    }

    CompletableFuture<Void> askPlayerForStarResourceReward() {
        if(numOfStarResourcesLeftToObtain != 0){
            return clientManager.askUserLocalized("client.cli.playerDashboard.notifyPlayerNumberOfResourcesLeftToObtain", numOfStarResourcesLeftToObtain)
                .thenCompose(resources -> {
                    int numOfResourceType = Integer.parseInt(resources.substring(0, 1));
                    ResourceType resourceType = ResourceType.getResourceTypeFromLocalizedName(resources.substring(2));
                    if(resourceType != null && numOfResourceType > 0 && numOfStarResourcesLeftToObtain >= numOfResourceType) {
                        numOfStarResourcesLeftToObtain -= numOfResourceType;
                        starResourcesReward.put(resourceType, numOfResourceType);
                        return askPlayerForStarResourceReward();
                    } else {
                        clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerResourcesAreInvalid");
                        return askPlayerForStarResourceReward();
                    }
                });
        }
        return askPlayerForProduction();
    }

    CompletableFuture<Void> sendPlayerChoiceToServer(){
        return clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
            new ProductionActionClientRequest(
                activePlayer,
                productions,
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
                    startProdSelectionDialog();
                    return CompletableFuture.completedFuture(null);
                }
            ).elseCompute(message -> {
                startProdSelectionDialog();
                return CompletableFuture.completedFuture(null);
                }).apply()
        );
    }
}
