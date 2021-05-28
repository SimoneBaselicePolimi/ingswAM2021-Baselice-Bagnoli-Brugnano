package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.view.grid.GridView;

public class GameView extends CliView {


    public GameView(CliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
        GridView externalGrid = new GridView(clientManager, 2, 1, 0, rowSize, columnSize);
        GridView upperRowGrid = new GridView(clientManager, 1, 2, 1);
        GridView bottomRowGrid = new GridView(clientManager, 1, 1, 1);

        externalGrid.setView(0, 0, upperRowGrid);
        externalGrid.setView(1, 0, bottomRowGrid);

        upperRowGrid.setView(0, 1, new GameHistoryView(clientManager));

        bottomRowGrid.setView(0, 0, new UserConsole(clientManager));

    }


}
