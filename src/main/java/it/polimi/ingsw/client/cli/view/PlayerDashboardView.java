//package it.polimi.ingsw.client.cli.view;
//
//import it.polimi.ingsw.client.cli.CliClientManager;
//import it.polimi.ingsw.client.cli.UserChoicesUtils;
//
//public class PlayerDashboardView extends CliView{
//
//    protected GameView gameView;
//    public PlayerDashboardView(CliClientManager clientManager, GameView gameView) {
//        super(clientManager);
//        this.gameView = gameView;
//        startPlayerDashBoardDialog();
//    }
//
//    void startPlayerDashBoardDialog() {
//        UserChoicesUtils.makeUserChoose(clientManager)
//            .addUserChoice(
//                () -> clientManager.askUserLocalized("client.cli.devCardTable.askForDevCardChoice")
//                    .thenCompose(input -> )
//                "client.cli.devCardTable.devCardChoice"
//            )
//            .addUserChoice(
//                () -> gameView.setMainContentView(new MainMenuView(clientManager)),
//                "client.cli.game.returnToMenu"
//            ).apply();
//    }
//}
