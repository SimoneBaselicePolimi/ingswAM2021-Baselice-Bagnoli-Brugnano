//package it.polimi.ingsw.client.cli.view;
//
//import it.polimi.ingsw.client.cli.CliClientManager;
//import it.polimi.ingsw.client.cli.UserChoicesUtils;
//import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
//import it.polimi.ingsw.network.clientrequest.DevelopmentActionClientRequest;
//
//import java.util.concurrent.CompletableFuture;
//
//public class DevCardTableView extends CliView{
//
//    protected GameView gameView;
//
//    public DevCardTableView(CliClientManager clientManager, GameView gameView) {
//        super(clientManager);
//        this.gameView = gameView;
//        startDevCardTableDialog();
//    }
//
//    void startDevCardTableDialog() {
//        UserChoicesUtils.makeUserChoose(clientManager)
//            .addUserChoice(
//                () -> askPlayerForDevCardChoice()
//                    .thenCompose(card ->
//                        clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
//                            new DevelopmentActionClientRequest(
//                                clientManager.getGameContextRepresentation().getActivePlayer(),
//
//                            )
//                        ))),
//                () -> clientManager.askUserLocalized("client.cli.devCardTable.askForDevCardChoice")
//                    .thenCompose(input -> )
//                "client.cli.devCardTable.devCardChoice"
//            )
//            .addUserChoice(
//                () -> gameView.setMainContentView(new MainMenuView(clientManager)),
//                "client.cli.game.returnToMenu"
//            ).apply();
//    }
//
//    CompletableFuture<Integer> askPlayerForDevCardChoice (){
//        return clientManager.askUserLocalized("client.cli.devCardTable.askForDevCardChoice")
//            .thenCompose(input -> {
//                int intInput = Integer.parseInt(input);
//                if (intInput >= clientManager.getGameContextRepresentation().getDevelopmentCardsTable(). || intInput < clientManager.getGameContextRepresentation().getMarket().getnRows())
//                    return CompletableFuture.completedFuture(intInput);
//                else {
//                    clientManager.tellUserLocalized("client.cli.market.notifyPlayerRowNumberIsInvalid");
//                    return askPlayerForRowNumber();
//                }
//            });
//    }
// }
