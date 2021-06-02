package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.network.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class PlayerDashboardView extends CliView{

    Player activePlayer = clientManager.getGameContextRepresentation().getActivePlayer();
    ClientPlayerContextRepresentation playerContextActivePlayer = clientManager.getGameContextRepresentation().getPlayerContext(activePlayer);
    //TODO
    //ClientProductionRepresentation?? La client request vuole una production reale
    protected Set<Production> productions;
    protected Map<ResourceType, Integer> starResourceCost;
    protected Map<ResourceType, Integer> starResourceReward;

    protected GameView gameView;
    public PlayerDashboardView(CliClientManager clientManager, GameView gameView) {
        super(clientManager);
        this.gameView = gameView;
        startPlayerDashBoardDialog();
    }

    void startPlayerDashBoardDialog() {
        UserChoicesUtils.makeUserChoose(clientManager)
            .addUserChoice(
                () -> askPlayerForProductions()
                    .thenCompose(input ->
                        clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
                            new ProductionActionClientRequest(
                                activePlayer,
                                productions,
                                starResourceCost,
                                starResourceReward
                            )
                    ))),
                "client.cli.playerDashboard.activateProduction"
            )
//            .addUserChoice(
//                () -> gameView.setMainContentView(new LeaderCardListView(
//                    clientManager.getGameContextRepresentation().getPlayerContext(clientManager.getMyPlayer())
//                )),
//                "client.cli.playerDashboard.leaderCardList"
//            )
            .addUserChoice(
                () -> gameView.setMainContentView(new MainMenuView(clientManager)),
                "client.cli.game.returnToMenu"
            ).apply();
    }

    CompletableFuture<Integer> askPlayerForProductions () {
        return askPlayerForStarResourceCost();
    }

    CompletableFuture<Integer> askPlayerForStarResourceCost () {
        return askPlayerForStarResourceReward();
    }

    CompletableFuture<Integer> askPlayerForStarResourceReward() {
        return null;
    }

}
