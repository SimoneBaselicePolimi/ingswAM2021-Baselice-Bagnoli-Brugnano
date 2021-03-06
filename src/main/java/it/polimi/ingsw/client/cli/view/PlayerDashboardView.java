package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.ProductionsSelectionInfo;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.server.model.Player;

/**
 * View representing each player's personal table
 */
public class PlayerDashboardView extends AbstractPlayerDashboardView {

    public PlayerDashboardView(Player dashboardPlayer, CliClientManager clientManager, GameView gameView) {
        super(dashboardPlayer, clientManager, gameView);
        startPlayerDashboardDialog();
    }

    void startPlayerDashboardDialog() {

        UserChoicesUtils.PossibleUserChoices choices = UserChoicesUtils.makeUserChoose(clientManager);
        if (dashboardPlayer.equals(clientManager.getMyPlayer())
            && clientManager.getGameState().equals(GameState.MY_PLAYER_TURN_BEFORE_MAIN_ACTION)) {
            choices.addUserChoiceLocalized(
                () -> gameView.setMainContentView(new ProductionSelectionDashboardView(
                    new ProductionsSelectionInfo(
                        clientManager.getGameContextRepresentation()
                            .getPlayerContext(activePlayer).getTotalResourcesOwnedByThePlayer()
                    ),
                    clientManager,
                    gameView
                )),
                "client.cli.playerDashboard.activateProduction"
            );
            choices.addUserChoiceLocalized(
                () -> gameView.setMainContentView(
                    new PlayerLeaderCardsInDashboardView(clientManager, dashboardPlayer, gameView)
                ),
                "client.cli.playerDashboard.leaderCardList"
            );
            choices.addUserChoiceLocalized(
                () -> gameView.setMainContentView(new MainMenuView(clientManager, gameView)),
                "client.cli.game.returnToMenu"
            );
        } else if (clientManager.getGameState().equals(GameState.GAME_SETUP)) {
            choices.addUserChoiceLocalized(
                () -> gameView.setMainContentView(new LeaderCardSetupView(clientManager, gameView)),
                "client.cli.game.returnToSetupView"
            );

        } else {
            choices.addUserChoiceLocalized(
                () -> gameView.setMainContentView(
                    new PlayerLeaderCardsInDashboardView(clientManager, dashboardPlayer, gameView)
                ),
                "client.cli.playerDashboard.leaderCardList"
            );
            choices.addUserChoiceLocalized(
                () -> gameView.setMainContentView(new MainMenuView(clientManager, gameView)),
                "client.cli.game.returnToMenu"
            );
        }

        choices.apply();
    }
}
