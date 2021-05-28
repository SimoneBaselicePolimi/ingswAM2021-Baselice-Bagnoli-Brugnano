package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;

public class DevCardTableView extends CliView{
    public DevCardTableView(CliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
    }

    public DevCardTableView(CliClientManager clientManager) {
        super(clientManager);
    }
}
