package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;

public class MarketView extends CliView{
    public MarketView(CliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
    }

    public MarketView(CliClientManager clientManager) {
        super(clientManager);
    }
}
