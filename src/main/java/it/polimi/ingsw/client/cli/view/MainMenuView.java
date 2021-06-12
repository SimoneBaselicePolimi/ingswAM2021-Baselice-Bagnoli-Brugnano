package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBufferUtils;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.cli.view.grid.LineBorderStyle;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.utils.Colour;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainMenuView extends CliView{

    protected GameView gameView;
    protected GridView outerGrid;
    protected LabelView playersInfo;

    public MainMenuView(CliClientManager clientManager, GameView gameView, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
        this.gameView = gameView;

        outerGrid = new GridView(clientManager, 2, 1, 1);
        outerGrid.setRowWeight(1, 19);
        addChildView(outerGrid, 0, 0);

        LabelView titleLabel = new LabelView(
            FormattedChar.convertStringToFormattedCharList(
                Localization.getLocalizationInstance().getString("client.mainMenu.gameTitle"),
                Colour.BLUE,
                Colour.BLACK,
                false,
                true,
                true
            ),
            clientManager
        );
        titleLabel.setHorizontalAlignment(FormattedCharsBufferUtils.HorizontalAlignment.CENTER);
        outerGrid.setView(0, 0, titleLabel);

        GridView playersGrid = new GridView(clientManager, 1, 1, 1);
        playersGrid.setBorderStyle(new LineBorderStyle());
        outerGrid.setView(1, 0, playersGrid);

        playersInfo = new LabelView(new ArrayList<>(), clientManager);
        playersGrid.setView(0, 0, playersInfo);

    }

    public MainMenuView(CliClientManager clientManager, GameView gameView) {
        this(clientManager, gameView, 0, 0);
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {

        playersInfo.setText(
            clientManager.getGameContextRepresentation().getPlayersOrder().stream()
                .flatMap(p ->
                    FormattedChar.convertStringToFormattedCharList(
                        " - " + p.playerName + "\n    "
                        + Localization.getLocalizationInstance().getString("client.mainMenu.resources")
                        + LocalizationUtils.getResourcesListAsCompactString(
                            clientManager.getGameContextRepresentation().getPlayerContext(p).getTotalResourcesOwnedByThePlayer())
                        + "\n    " + Localization.getLocalizationInstance().getString("client.mainMenu.faithPositions")
                        + clientManager.getGameContextRepresentation().getFaithPath().getFaithPositions().get(p)
                        + "\n\n",
                        clientManager.getGameContextRepresentation().getActivePlayer().equals(p) ?
                            Colour.GREEN : Colour.WHITE,
                        Colour.BLACK
                    ).stream()
                ).collect(Collectors.toList())
        );

        return super.getContentAsFormattedCharsBuffer();
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
