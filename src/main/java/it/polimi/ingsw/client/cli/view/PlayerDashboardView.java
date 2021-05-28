package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;

public class PlayerDashboardView extends CliView{
    public PlayerDashboardView(CliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
    }

    public PlayerDashboardView(CliClientManager clientManager) {
        super(clientManager);
    }
}
