package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;

public class MainMenuView extends CliView{
    public MainMenuView(CliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
    }

    public MainMenuView(CliClientManager clientManager) {
        super(clientManager);
    }
}
