package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class PlayerDashboardView extends AbstractPlayerDashboardView {


    public PlayerDashboardView(Player dashboardPlayer, CliClientManager clientManager, GameView gameView) {
        super(dashboardPlayer, clientManager, gameView);
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
        } else if (!clientManager.getMyPlayer().equals(activePlayer)) {//game setup or my Player is not the active player
            UserChoicesUtils.makeUserChoose(clientManager)
                .addUserChoice(
                    () -> gameView.setMainContentView(new LeaderCardsInDashBoardView(
                        clientManager,
                        clientManager.getMyPlayer()
                    )),
                    "client.cli.playerDashboard.leaderCardList"
                )
                .addUserChoice(
                    () -> gameView.setMainContentView(new MainMenuView(clientManager)),
                    "client.cli.game.returnToMenu"
                ).apply();
        }
    }
}
