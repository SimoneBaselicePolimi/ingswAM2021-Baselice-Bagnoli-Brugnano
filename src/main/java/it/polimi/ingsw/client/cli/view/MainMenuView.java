package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;

public class MainMenuView extends CliView{

    GameView gameView;

    public MainMenuView(CliClientManager clientManager, GameView gameView, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
        this.gameView = gameView;
    }

    public MainMenuView(CliClientManager clientManager, GameView gameView) {
        this(clientManager, gameView, 0, 0);
    }
}
