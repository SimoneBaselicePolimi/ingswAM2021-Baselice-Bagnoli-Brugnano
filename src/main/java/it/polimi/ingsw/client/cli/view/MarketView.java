package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.utils.Colour;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.MarketActionFetchColumnClientRequest;
import it.polimi.ingsw.client.clientrequest.MarketActionFetchRowClientRequest;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.marketrepresentation.ClientMarketRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.server.model.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MarketView extends CliView{

    public static final int MARKET_ROW_SIZE = 14;
    public static final int MARKET_COL_SIZE = 30;

    protected GridView outerGrid;
    protected ClientMarketRepresentation marketRepresentation;

    protected GameView gameView;
    Player activePlayer;
    ClientPlayerContextRepresentation activePlayerContext;

    public MarketView(CliClientManager clientManager, GameView gameView) {
        super(clientManager);
        this.gameView = gameView;

        startMarketDialog();

        activePlayer = clientManager.getGameContextRepresentation().getActivePlayer();
        activePlayerContext = clientManager.getGameContextRepresentation().getPlayerContext(activePlayer);

        outerGrid = new GridView(clientManager, 1, 2, 0);
        addChildView(outerGrid, 0, 0);

        GridView marketGrid = new GridView(clientManager, 3, 4, 1);
        outerGrid.setView(0, 0, marketGrid);

        for(int r=0; r<marketGrid.getViewRowSize(); r++)
            for(int c=0; c<marketGrid.getViewColumnSize(); c++) {
                LabelView cellView = new LabelView(
                    List.of(new FormattedChar(
                        ' ',
                        Colour.WHITE,
                        marketRepresentation.getMatrix()[r][c].getMarbleColour().get(0) //TODO check other colours
                        )
                    ),
                    clientManager
                );
                marketGrid.setView(r, c, cellView);
            }

        GridView legendGrid = new GridView(clientManager, 1, 1, 1);
        outerGrid.setView(1,0, legendGrid);
    }

    void startMarketDialog() {

        //game setup or my Player is not the active player
        if(clientManager.getGameState().equals(GameState.GAME_SETUP) || !clientManager.getMyPlayer().equals(activePlayer)){
            UserChoicesUtils.makeUserChoose(clientManager)
                .addUserChoice(
                    () -> gameView.setMainContentView(new MainMenuView(clientManager)),
                    "client.cli.game.returnToMenu"
                ).apply();
        }

        //game started and my player is the active player
        else{
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

    @Override
    public void setRowSize(int rowSize) {
        outerGrid.setRowSize(rowSize);
        super.setRowSize(rowSize);
    }

    @Override
    public void setColumnSize(int columnSize) {
        outerGrid.setColumnSize(columnSize);
        super.setColumnSize(columnSize);
    }
}
