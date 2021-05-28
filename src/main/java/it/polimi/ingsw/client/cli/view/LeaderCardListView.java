package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;

public class LeaderCardListView extends CliView{
    public LeaderCardListView(CliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
    }

    public LeaderCardListView(CliClientManager clientManager) {
        super(clientManager);
    }
}
