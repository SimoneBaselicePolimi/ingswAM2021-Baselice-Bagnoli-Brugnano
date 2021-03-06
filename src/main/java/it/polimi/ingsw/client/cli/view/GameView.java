package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.cli.view.grid.LineBorderStyle;

/**
 * Main view of the game where the various components are shown.
 * In fact, a screen is composed of the GameHistory, the Console and the GameView that shows to the user game components
 */
public class GameView extends CliView {

    protected CliView currentContentView = null;

    protected GridView externalGrid, upperRowGrid, bottomRowGrid;

    public GameView(CliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
        externalGrid = new GridView(clientManager, 2, 1, 0, rowSize, columnSize);
        upperRowGrid = new GridView(clientManager, 1, 2, 1);
        bottomRowGrid = new GridView(clientManager, 1, 1, 1);

        externalGrid.setRowWeight(0, 5);
        externalGrid.setRowWeight(1, 3);
        upperRowGrid.setColWeight(0, 3);

        upperRowGrid.setBorderStyle(new LineBorderStyle());
        bottomRowGrid.setBorderStyle(new LineBorderStyle());

        addChildView(externalGrid, 0, 0);

        externalGrid.setView(0, 0, upperRowGrid);
        externalGrid.setView(1, 0, bottomRowGrid);

        upperRowGrid.setView(0, 1, new GameHistoryView(clientManager));

        UserConsole console = new UserConsole(clientManager);
        bottomRowGrid.setView(0, 0, console);
        clientManager.setNewUserIOLogger(console);

    }

    public void setMainContentView(CliView contentView) {
        if(currentContentView != null) {
            currentContentView.destroyView();
        }
        upperRowGrid.children.removeIf(c -> c.getView().equals(currentContentView));
        currentContentView = contentView;
        upperRowGrid.setView(0, 0, contentView);
        updateView();
    }


}
