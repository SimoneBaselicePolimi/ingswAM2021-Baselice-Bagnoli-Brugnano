package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;

public class FaithPathView extends CliView{
    public FaithPathView(CliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
    }

    public FaithPathView(CliClientManager clientManager) {
        super(clientManager);
    }
}
