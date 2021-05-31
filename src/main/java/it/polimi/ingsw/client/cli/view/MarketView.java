package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.network.clientrequest.MarketActionFetchColumnClientRequest;
import it.polimi.ingsw.network.clientrequest.MarketActionFetchRowClientRequest;

import java.util.concurrent.CompletableFuture;

public class MarketView extends CliView{

    protected GameView gameView;

    public MarketView(CliClientManager clientManager, GameView gameView) {
        super(clientManager);
        this.gameView = gameView;
        startMarketDialog();
    }

    void startMarketDialog() {

        //if(clientManager.getGameState().equals(GameState.GAME_SETUP))

        UserChoicesUtils.makeUserChoose(clientManager)
            .addUserChoice(
                () -> askPlayerForRowNumber()
                    .thenCompose(rowNumber ->
                        clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
                            new MarketActionFetchRowClientRequest(
                                clientManager.getGameContextRepresentation().getActivePlayer(),
                                rowNumber
                            )
                    ))),
                "client.cli.market.rowChoice"
            ).addUserChoice(
                () -> askPlayerForColumnNumber()
                    .thenCompose(columnNumber ->
                        clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
                            new MarketActionFetchColumnClientRequest(
                                clientManager.getGameContextRepresentation().getActivePlayer(),
                                columnNumber
                            )
                    ))),
                "client.cli.market.columnChoice"
            ).addUserChoice(
                () -> gameView.setMainContentView(new MainMenuView(clientManager)),
                "client.cli.game.returnToMenu"
            ).apply();
    }

    CompletableFuture<Integer> askPlayerForRowNumber (){
        return clientManager.askUserLocalized("client.cli.market.askForRowNumber")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                if (intInput >= 0 || intInput < clientManager.getGameContextRepresentation().getMarket().getnRows())
                    return CompletableFuture.completedFuture(intInput);
                else {
                    clientManager.tellUserLocalized("client.cli.market.notifyPlayerRowNumberIsInvalid");
                    return askPlayerForRowNumber();
                }
            });
    }

    CompletableFuture<Integer> askPlayerForColumnNumber (){
        return clientManager.askUserLocalized("client.cli.market.askForColumnNumber")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                if (intInput >= 0 || intInput < clientManager.getGameContextRepresentation().getMarket().getnColumns())
                    return CompletableFuture.completedFuture(intInput);
                else {
                    clientManager.tellUserLocalized("client.cli.market.notifyPlayerColumnNumberIsInvalid");
                    return askPlayerForColumnNumber();
                }
            });
    }
}
