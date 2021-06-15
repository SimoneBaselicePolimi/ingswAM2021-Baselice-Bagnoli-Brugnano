package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.cli.view.grid.LineBorderStyle;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.MarketActionFetchColumnClientRequest;
import it.polimi.ingsw.client.clientrequest.MarketActionFetchRowClientRequest;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.marketrepresentation.ClientMarketRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientMarbleColourRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.Colour;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MarketView extends CliView{

    protected GridView outerGrid;
    protected GridView marketGrid;
    protected GridView legend;

    protected ClientMarketRepresentation marketRepresentation;

    protected GameView gameView;
    Player activePlayer;
    ClientPlayerContextRepresentation activePlayerContext;

    public MarketView(CliClientManager clientManager, GameView gameView) {
        super(clientManager);
        this.gameView = gameView;

        activePlayer = clientManager.getGameContextRepresentation().getActivePlayer();
        activePlayerContext = clientManager.getGameContextRepresentation().getPlayerContext(activePlayer);
        marketRepresentation = clientManager.getGameContextRepresentation().getMarket();

        outerGrid = new GridView(clientManager, 1, 2, 1);
        addChildView(outerGrid, 0, 0);

        marketGrid = new GridView(
            clientManager,
            marketRepresentation.getNumberOfRows(),
            marketRepresentation.getNumberOfColumns() + 1,
            0
        );
        outerGrid.setView(0, 0, marketGrid);

        for(int r=0; r<marketRepresentation.getNumberOfRows(); r++)
            for(int c=0; c<marketRepresentation.getNumberOfColumns(); c++) {
                GridView cellGrid = new GridView(clientManager, 1,1, 1);
                cellGrid.setBorderStyle(new LineBorderStyle());
                marketGrid.setView(r, c, cellGrid);
                LabelView cellView = new LabelView(new ArrayList<>(), clientManager);
                cellView.setBackgroundChar(
                    new FormattedChar(
                        ' ',
                        Colour.WHITE,
                        marketRepresentation.getMatrix()[r][c].getMarbleColour().get(0)      //TODO check other colours
                    )
                );
                cellGrid.setView(0, 0, cellView);
            }

        GridView outMarbleGrid = new GridView(clientManager, 1, 1, 1);
        outMarbleGrid.setBorderStyle(new LineBorderStyle());
        marketGrid.setView(0, marketRepresentation.getNumberOfColumns(), outMarbleGrid);
        LabelView outMarbleView = new LabelView(new ArrayList<>(), clientManager);
        outMarbleView.setBackgroundChar(
            new FormattedChar(
                ' ',
                Colour.WHITE,
                marketRepresentation.getOutMarble().getMarbleColour().get(0)       //TODO check other colours
            )
        );
        outMarbleGrid.setView(0,0, outMarbleView);

        Set<ClientMarbleColourRepresentation> differentMarbleColours = new HashSet<>(
            clientManager.getGameItemsManager().getAllItemsOfType(ClientMarbleColourRepresentation.class)
        );

        legend = new GridView(clientManager, differentMarbleColours.size(), 2, 1);
        legend.setBorderStyle(new LineBorderStyle());
        legend.setColWeight(1, 3);
        outerGrid.setView(0,1, legend);

        int numOfRow = 0;
        for(ClientMarbleColourRepresentation marble : differentMarbleColours) {
            Colour colour = marble.getMarbleColour().get(0);
            LabelView legendCell = new LabelView(new ArrayList<>(), clientManager);
            legendCell.setBackgroundChar(new FormattedChar(
                ' ',
                Colour.WHITE,
                new Colour(colour.r, colour.g, colour.b)
            ));
            legend.setView(numOfRow, 0, legendCell);
            LabelView marbleDescriptionCell = new LabelView(
                FormattedChar.convertStringToFormattedCharList(getMarbleDescription(marble)),
                clientManager
            );
            legend.setView(numOfRow, 1, marbleDescriptionCell);
            numOfRow++;
        }

        startMarketDialog();

    }

    void startMarketDialog() {

        //game setup
        if(clientManager.getGameState().equals(GameState.GAME_SETUP)){
            UserChoicesUtils.makeUserChoose(clientManager)
                .addUserChoiceLocalized(
                    () -> gameView.setMainContentView(new LeaderCardSetupView(clientManager, gameView)),
                    "client.cli.game.returnToSetupView"
                ).apply();
        } else if(!clientManager.getMyPlayer().equals(activePlayer)){ //game started and my Player is not the active player
            UserChoicesUtils.makeUserChoose(clientManager)
                .addUserChoiceLocalized(
                    () -> gameView.setMainContentView(new MainMenuView(clientManager, gameView)),
                    "client.cli.game.returnToMenu"
                ).apply();
        } else { //game started and my player is the active player
            UserChoicesUtils.makeUserChoose(clientManager)
                .addUserChoiceLocalized(
                    () -> askPlayerForRowNumber()
                        .thenCompose(rowNumber ->
                            clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
                                new MarketActionFetchRowClientRequest(
                                    clientManager.getGameContextRepresentation().getActivePlayer(),
                                    rowNumber
                                )
                            ))),
                    "client.cli.market.rowChoice"
                ).addUserChoiceLocalized(
                () -> askPlayerForColumnNumber()
                    .thenCompose(columnNumber ->
                        clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
                            new MarketActionFetchColumnClientRequest(
                                clientManager.getGameContextRepresentation().getActivePlayer(),
                                columnNumber
                            )
                        ))),
                "client.cli.market.columnChoice"
            ).addUserChoiceLocalized(
                () -> gameView.setMainContentView(new MainMenuView(clientManager, gameView)),
                "client.cli.game.returnToMenu"
            ).apply();
        }
    }


    CompletableFuture<Integer> askPlayerForRowNumber (){
        return clientManager.askUserLocalized("client.cli.market.askForRowNumber")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                if (intInput >= 0 || intInput < clientManager.getGameContextRepresentation().getMarket().getNumberOfRows())
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
                if (intInput >= 0 || intInput < clientManager.getGameContextRepresentation().getMarket().getNumberOfColumns())
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

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {
        return super.getContentAsFormattedCharsBuffer();
    }

    private String getMarbleDescription(ClientMarbleColourRepresentation marble) {
        StringBuilder descriptionBuilder = new StringBuilder();
        if(marble.getResourceType().isPresent()) {
            descriptionBuilder.append(
                LocalizationUtils.getResourcesListAsCompactString(
                    Map.of(marble.getResourceType().get(), 1)
                )
            );
            if(marble.getFaithPoints()>0 || marble.isSpecialMarble())
                descriptionBuilder.append(", ");
            else
                return descriptionBuilder.toString();
        }
        if(marble.getFaithPoints()>0) {
            descriptionBuilder.append(marble.getFaithPoints());
            descriptionBuilder.append(" ");
            descriptionBuilder.append(
                marble.getFaithPoints() == 1 ?
                    Localization.getLocalizationInstance().getString("gameHistory.faithPath.faithPoints.singular")
                : Localization.getLocalizationInstance().getString("gameHistory.faithPath.faithPoints.plural")
            );
            if(marble.isSpecialMarble())
                descriptionBuilder.append(", ");
        }
        if(marble.isSpecialMarble())
            descriptionBuilder.append(
                Localization.getLocalizationInstance().getString("marbles.special")
            );
        return descriptionBuilder.toString();
    }
}
