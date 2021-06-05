package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class PlayerDashboardView extends CliView{

    Player activePlayer;
    ClientPlayerContextRepresentation activePlayerContext;

    private int numOfStarResourcesLeftToPay = 0;
    private int numOfStarResourcesLeftToObtain = 0;
    private Map<ResourceType, Integer> totalNonStarResourcesCost = new HashMap<>();

    protected Set<ClientProductionRepresentation> productions;
    protected Map<ResourceType, Integer> starResourcesCost = new HashMap<>();
    protected Map<ResourceType, Integer> starResourcesReward = new HashMap<>();

    protected GameView gameView;
    public PlayerDashboardView(CliClientManager clientManager, GameView gameView) {

        super(clientManager);
        this.gameView = gameView;

        activePlayer = clientManager.getGameContextRepresentation().getActivePlayer();
        activePlayerContext = clientManager.getGameContextRepresentation().getPlayerContext(activePlayer);

        startPlayerDashboardDialog();
    }

    void startPlayerDashboardDialog() {

        //game setup
        if (clientManager.getGameState().equals(GameState.GAME_SETUP)) {
            UserChoicesUtils.makeUserChoose(clientManager)
                .addUserChoiceLocalized(
                    () -> gameView.setMainContentView(new LeaderCardSetupView(clientManager, gameView)),
                    "client.cli.game.returnToSetupView"
                ).apply();
        } else if(!clientManager.getMyPlayer().equals(activePlayer)) {//game setup or my Player is not the active player
            UserChoicesUtils.makeUserChoose(clientManager)
                .addUserChoice(
                    () -> gameView.setMainContentView(new LeaderCardsInDashBoardView(clientManager, clientManager.getMyPlayer())),
                    "client.cli.playerDashboard.leaderCardList"
                )
                .addUserChoice(
                    () -> gameView.setMainContentView(new MainMenuView(clientManager)),
                    "client.cli.game.returnToMenu"
                ).apply();
        }
        //game started and my player is the active player
        else {
            UserChoicesUtils.makeUserChoose(clientManager)
                .addUserChoice(
                    () -> askPlayerForTypeOfProductions()
                        .thenCompose(input ->
                            clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
                                new ProductionActionClientRequest(
                                    activePlayer,
                                    productions,
                                    starResourcesCost,
                                    starResourcesReward
                                )
                            ))),
                    "client.cli.playerDashboard.activateProduction"
                )
                .addUserChoice(
                    () -> gameView.setMainContentView(new LeaderCardsInDashBoardView(clientManager, clientManager.getMyPlayer())),
                    "client.cli.playerDashboard.leaderCardList"
                )
                .addUserChoice(
                    () -> gameView.setMainContentView(new MainMenuView(clientManager)),
                    "client.cli.game.returnToMenu"
                ).apply();
        }
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
            .addUserChoice(
                () -> askPlayerForLeaderCardsProduction(),
                "client.cli.playerDashboard.activateLeaderCardsProduction"
            )
            .addUserChoice(
                () -> sumResources(),
                "client.cli.playerDashboard.endProductionsActivation"
            ).apply();
    }

    CompletableFuture<Void> askPlayerForDashboardProductions () {
        Set<ClientProductionRepresentation> productionsThePlayerCanActivate = activePlayerContext.getActiveProductions();
        return clientManager.askUserLocalized("client.cli.playerDashboard.askPlayerForDevProductionsChoice")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                if(intInput > 0 && intInput <= activePlayerContext.getDevelopmentCardDecks().size()) {
                    productions.add(activePlayerContext.getDevelopmentCardDecks().get(intInput-1).peek().getProduction());
                    return askPlayerForTypeOfProductions();
                } else {
                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionNumberIsInvalid");
                    return askPlayerForTypeOfProductions();
                }
            });
    }

    CompletableFuture<Void> askPlayerForBaseProduction() {
        return clientManager.askUserLocalized("client.cli.playerDashboard.askPlayerForBaseProductionChoice")
            .thenCompose(input -> {
                    int intInput = Integer.parseInt(input);
                    if(intInput > 0 && intInput<activePlayerContext.getBaseProductions().size()) {
                        productions.add(new ArrayList<ClientProductionRepresentation>(activePlayerContext.getBaseProductions()).get(intInput-1));
                        return askPlayerForTypeOfProductions();
                    }
                    else {
                        clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionNumberIsInvalid");
                        return askPlayerForTypeOfProductions();
                    }
            });
    }

    CompletableFuture<Void> askPlayerForLeaderCardsProduction () {
        return clientManager.askUserLocalized("client.cli.playerDashboard.askPlayerForLeaderProductionsChoice")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                if (intInput > 0 && intInput < activePlayerContext.getActiveLeaderCardsProductions().size()) {
                    productions.add(new ArrayList<ClientProductionRepresentation>(activePlayerContext.getActiveLeaderCardsProductions()).get(intInput-1));
                    return askPlayerForTypeOfProductions();
                } else {
                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionNumberIsInvalid");
                    return askPlayerForTypeOfProductions();
                }
            });
    }

    private CompletableFuture<Void> sumResources(){
        for (ClientProductionRepresentation production : productions) {
            if (production.getStarResourceCost() != 0)
                numOfStarResourcesLeftToPay += production.getStarResourceCost();
            if (production.getStarResourceReward() != 0)
                numOfStarResourcesLeftToObtain += production.getStarResourceReward();
            totalNonStarResourcesCost = ResourceUtils.sum(
                totalNonStarResourcesCost,
                production.getResourceCost()
            );
        }
        return askPlayerForStarResourcesCost();
    }


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
        return checkIfThePlayerHasNecessaryResources();
    }

    CompletableFuture<Void> checkIfThePlayerHasNecessaryResources() {
        Map<ResourceType, Integer> totalResourcesCost = ResourceUtils.sum(
            totalNonStarResourcesCost,
            starResourcesCost
        );

        if (!ResourceUtils.areResourcesAContainedInB(
            totalResourcesCost,
            activePlayerContext.getTotalResourcesOwnedByThePlayer())
        ) {
            clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerHeDoesNotHaveNeededResources");
            productions.clear();
            starResourcesCost.clear();
            starResourcesReward.clear();
            return askPlayerForTypeOfProductions();
        }
        else {
            return askPlayerForStarResourceReward();
        }
    }

    CompletableFuture<Void> askPlayerForStarResourceReward() {

        if(numOfStarResourcesLeftToObtain != 0){
            return clientManager.askUserLocalized("client.cli.playerDashboard.notifyPlayerNumberOfResourcesLeftToObtain", numOfStarResourcesLeftToObtain)
                .thenCompose(resources ->{
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

        return CompletableFuture.completedFuture(null);
    }
}
