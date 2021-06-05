package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.cli.view.grid.LineBorderStyle;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation.ClientFaithPathRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation.ClientVaticanReportSectionRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import it.polimi.ingsw.utils.Colour;

import java.util.ArrayList;
import java.util.List;

public class FaithPathView extends CliView{

    protected GameView gameView;
    protected GridView outerGrid;
    protected GridView faithPathGrid;
    protected LabelView faithCellView;
    protected LabelView vaticanReportCellView;
    protected GridView playerInfoView;
    protected GridView popeCardGrid;
    protected List<GridView> popeFavorCardStateList;

    protected ClientFaithPathRepresentation faithPathRepresentation;

    protected Player myPlayer;
    protected List<Player> playersInOrder;

    public FaithPathView(CliClientManager clientManager, GameView gameView) {
        super(clientManager);
        this.gameView = gameView;

        startFaithPathDialog();

        faithPathRepresentation = clientManager.getGameContextRepresentation().getFaithPath();

        myPlayer = clientManager.getMyPlayer();
        playersInOrder = clientManager.getGameContextRepresentation().getPlayersOrder();

        outerGrid = new GridView(clientManager, 2, 1, 1);
        addChildView(outerGrid, 0, 0);

        faithPathGrid = new GridView(clientManager, 1, faithPathRepresentation.getFaithPathLength(), 1);
        faithPathGrid.setBorderStyle(new LineBorderStyle());
        outerGrid.setView(0, 0, faithPathGrid);

        playerInfoView = new GridView(
            clientManager,
            1,
            faithPathRepresentation.getPopeFavorCards().get(myPlayer).size() + 1, //TODO
            0
        );
        playerInfoView.setColWeight(faithPathRepresentation.getPopeFavorCards().get(myPlayer).size(), 2);
        outerGrid.setView(1, 0, playerInfoView);

        int colIndex = 1;
        for (PopeFavorCardState popeFavorCardState : faithPathRepresentation.getPopeFavorCards().get(myPlayer)) {
            popeCardGrid = new GridView(clientManager, 1, 1, 1);
            popeFavorCardStateList.add(popeCardGrid);
            playerInfoView.setView(0, colIndex, popeCardGrid);

            LabelView popeCardState = new LabelView(
                FormattedChar.convertStringToFormattedCharList(
                    popeFavorCardState.toString(),
                    Colour.WHITE,
                    Colour.BLACK
                ),
                clientManager
            );
            popeCardGrid.setView(0, 0, popeCardState);
            colIndex++;
        }

        LabelView playerPositions = new LabelView(
            FormattedChar.convertStringToFormattedCharList(
                getPositionOfPlayersAsString(playersInOrder)
            ),
            clientManager
        );
        playerInfoView.setView(
            0,
            faithPathRepresentation.getPopeFavorCards().get(myPlayer).size(),
            playerPositions
        );

        int cell = 0;
        for (ClientVaticanReportSectionRepresentation vaticanReportSection : faithPathRepresentation.getVaticanReportSections()) {
            while (cell < faithPathRepresentation.getFaithPathLength()) {
                if (cell < vaticanReportSection.getSectionInitialPos()) {
                    faithCellView = new LabelView(new ArrayList<>(), clientManager);
                    faithCellView.setBackgroundChar(
                        new FormattedChar(
                            ' ',
                            Colour.WHITE,
                            Colour.WHITE
                        )
                    );
                    faithCellView.setText(
                        FormattedChar.convertStringToFormattedCharList(String.valueOf(cell), Colour.BLACK, Colour.WHITE)
                    );
                    faithPathGrid.setView(0, cell, faithCellView);
                    cell++;
                } else if (cell == vaticanReportSection.getSectionInitialPos()) {
                    while (cell <= vaticanReportSection.getPopeSpacePos()) {
                        vaticanReportCellView = new LabelView(new ArrayList<>(), clientManager);
                        vaticanReportCellView.setBackgroundChar(
                            new FormattedChar(
                                ' ',
                                Colour.WHITE,
                                Colour.BLUE)
                        );
                        vaticanReportCellView.setText(
                            FormattedChar.convertStringToFormattedCharList(String.valueOf(cell), Colour.WHITE, Colour.BLUE)
                        );
                        faithPathGrid.setView(0, cell, vaticanReportCellView);
                        cell++;
                    }
                    break;
                }
            }
        }
    }

    private String getPositionOfPlayersAsString(List<Player> playersInOrder) {
        StringBuilder positionOfPlayers = new StringBuilder();
        for(Player player : playersInOrder) {
            positionOfPlayers.append(player.playerName).append(": ");
            positionOfPlayers.append(
                Localization.getLocalizationInstance().getString("gameHistory.faithPath.position")
            );
            positionOfPlayers.append(faithPathRepresentation.getFaithPositions().get(player));
            positionOfPlayers.append("\n");
        }
        return positionOfPlayers.toString();
    }

    void startFaithPathDialog() {
        if(clientManager.getGameState().equals(GameState.GAME_SETUP)) {
            UserChoicesUtils.makeUserChoose(clientManager)
                .addUserChoiceLocalized(
                    () -> gameView.setMainContentView(new LeaderCardSetupView(clientManager, gameView)),
                    "client.cli.game.returnToSetupView"
                ).apply();
        }else {
            UserChoicesUtils.makeUserChoose(clientManager)
                .addUserChoice(
                    () -> gameView.setMainContentView(new MainMenuView(clientManager)),
                    "client.cli.game.returnToMenu"
                ).apply();
        }
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
        for (int i=0; i<popeFavorCardStateList.size(); i++) {
            PopeFavorCardState popeFavorCardState = faithPathRepresentation.getPopeFavorCards().get(myPlayer).get(i);
            if (popeFavorCardState == PopeFavorCardState.ACTIVE)
                popeFavorCardStateList.get(i).setBorderStyle(new LineBorderStyle(Colour.GREEN));
            else if (popeFavorCardState == PopeFavorCardState.DISCARDED)
                popeFavorCardStateList.get(i).setBorderStyle(new LineBorderStyle(Colour.RED));
            else
                popeFavorCardStateList.get(i).setBorderStyle(new LineBorderStyle(Colour.WHITE));
        }
        return super.getContentAsFormattedCharsBuffer();
    }

}
