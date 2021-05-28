package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;

public class LeaderCardView extends CliView{
    public LeaderCardView(CliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
    }

    public LeaderCardView(CliClientManager clientManager) {
        super(clientManager);
    }
}
