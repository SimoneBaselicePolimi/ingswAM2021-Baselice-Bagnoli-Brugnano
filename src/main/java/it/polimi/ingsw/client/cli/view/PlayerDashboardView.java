package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class PlayerDashboardView extends CliView{

    Player activePlayer = clientManager.getGameContextRepresentation().getActivePlayer();
    ClientPlayerContextRepresentation playerContextActivePlayer = clientManager.getGameContextRepresentation().getPlayerContext(activePlayer);

    private int numberOfResourcesLeftToPay = 0;
    private int numberOfResourcesLeftToObtain = 0;
    private int totalStarResourcesCost = 0;
    private int totalStarResourcesRewards = 0;

    protected Set<ClientProductionRepresentation> productionsWithStarCost;
    protected Set<ClientProductionRepresentation> productionsWithStarReward;
    protected Set<ClientProductionRepresentation> productions;
    protected Set<ClientProductionRepresentation> finalProductions;
    protected Map<ResourceType, Integer> starResourceCost;
    protected Map<ResourceType, Integer> starResourceReward;

    protected GameView gameView;
    public PlayerDashboardView(CliClientManager clientManager, GameView gameView) {
        super(clientManager);
        this.gameView = gameView;
        startPlayerDashBoardDialog();
    }

    void startPlayerDashBoardDialog() {

        //game setup or my Player is not the active player
        if(clientManager.getGameState().equals(GameState.GAME_SETUP) || !clientManager.getMyPlayer().equals(activePlayer)){
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
                                    finalProductions,
                                    starResourceCost,
                                    starResourceReward
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

    CompletableFuture<Integer> askPlayerForTypeOfProductions() {
        UserChoicesUtils.makeUserChoose(clientManager)
            .addUserChoice(
                () -> askPlayerForBaseProduction(),
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
                () -> CompletableFuture.completedFuture(1),
                "client.cli.playerDashboard.endProductionsActivation"
            ).apply();
        return null;
    }

    CompletableFuture<Integer> askPlayerForDashboardProductions () {
        Set<ClientProductionRepresentation> productionsThePlayerCanActivate = playerContextActivePlayer.getActiveProductions();
        return clientManager.askUserLocalized("client.cli.playerDashboard.askPlayerForProductionsChoice")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                if(intInput > 0 && intInput <= playerContextActivePlayer.getDevelopmentCardDecks().size()){
                    ClientProductionRepresentation production = playerContextActivePlayer.getDevelopmentCardDecks().get(intInput).peek().getProduction();
                    if (productionsThePlayerCanActivate.contains(production)) {
                        productions.add(production);
                        return checkThePlayerHasNecessaryResources();
                    }
                    else{
                        clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionIsNotActivable");
                        return askPlayerForTypeOfProductions();
                    }
                }
                else{
                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerDeckNumberIsInvalid");
                    return askPlayerForTypeOfProductions();
                }
            });
    }

    CompletableFuture<Integer> askPlayerForBaseProduction() {
        productions.addAll(playerContextActivePlayer.getBaseProductions());
        return checkThePlayerHasNecessaryResources();
    }

    CompletableFuture<Integer> askPlayerForLeaderCardsProduction () {
        Set<ClientProductionRepresentation> productionsThePlayerCanActivate = playerContextActivePlayer.getActiveProductions();
        return clientManager.askUserLocalized("client.cli.playerDashboard.askPlayerForProductionsChoice")
            .thenCompose(input ->{
                int intInput = Integer.parseInt(input);
                if (intInput > 0 && intInput < playerContextActivePlayer.getActiveLeaderCardsProductions().size()){
                    ClientProductionRepresentation production = new ArrayList<ClientProductionRepresentation>(playerContextActivePlayer.getActiveLeaderCardsProductions()).get(intInput);
                    if(productionsThePlayerCanActivate.contains(production)){
                        productions.add(production);
                        return checkThePlayerHasNecessaryResources();
                    }
                    else{
                        clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionIsNotActivable");
                        return askPlayerForTypeOfProductions();
                    }
                }
                else {
                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionNumberIsInvalid");
                    return askPlayerForTypeOfProductions();
                }
            });
    }

    private CompletableFuture<Integer> checkThePlayerHasNecessaryResources(){
        for(ClientProductionRepresentation production : productions) {
            if (production.getStarResourceCost() != 0)
                totalStarResourcesCost += production.getStarResourceCost();
            if (production.getStarResourceReward() != 0)
                totalStarResourcesRewards += production.getStarResourceReward();
        }
        numberOfResourcesLeftToPay = totalStarResourcesCost;
        numberOfResourcesLeftToObtain = totalStarResourcesRewards;
        return null;
    }

    CompletableFuture<Integer> askPlayerForStarResourceCost() {
        if(numberOfResourcesLeftToPay != 0){
            return clientManager.askUserLocalized("client.cli.playerDashboard.notifyPlayerNumberOfResourcesLeftToPay", numberOfResourcesLeftToPay)
                .thenCompose(resources -> {
                    int numOfResourceType = Integer.parseInt(resources.substring(0, 1));
                    ResourceType resourceType = ResourceType.getResourceTypeFromLocalizedName(resources.substring(2));
                    if(resourceType != null && numOfResourceType > 0 && numberOfResourcesLeftToPay >= numOfResourceType) {
                        numberOfResourcesLeftToPay -= numOfResourceType;
                        starResourceCost.put(resourceType, numOfResourceType);
                        return askPlayerForStarResourceCost();
                    } else {
                        clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerResourcesAreInvalid");
                        return askPlayerForStarResourceCost();
                    }
                });
        }
        return askPlayerForStarResourceReward();
    }

    CompletableFuture<Integer> askPlayerForStarResourceReward() {
        if(numberOfResourcesLeftToObtain != 0){
            return clientManager.askUserLocalized("client.cli.playerDashboard.notifyPlayerNumberOfResourcesLeftToObtain", numberOfResourcesLeftToObtain)
                .thenCompose(resources ->{
                    int numOfResourceType = Integer.parseInt(resources.substring(0, 1));
                    ResourceType resourceType = ResourceType.getResourceTypeFromLocalizedName(resources.substring(2));
                    if(resourceType != null && numOfResourceType > 0 && numberOfResourcesLeftToObtain >= numOfResourceType) {
                        numberOfResourcesLeftToObtain -= numOfResourceType;
                        starResourceReward.put(resourceType, numOfResourceType);
                        return askPlayerForStarResourceReward();
                    } else {
                        clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerResourcesAreInvalid");
                        return askPlayerForStarResourceReward();
                    }
                });
        }
        productions.addAll(productions);
        productions.clear();
        return askPlayerForTypeOfProductions();
    }
}
