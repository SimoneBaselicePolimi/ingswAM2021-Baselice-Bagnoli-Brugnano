package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;

public class FaithPathView extends CliView{

    protected GameView gameView;

    public FaithPathView(CliClientManager clientManager, GameView gameView) {
        super(clientManager);
        this.gameView = gameView;
        startFaithPathDialog();
    }

    void startFaithPathDialog() {
        UserChoicesUtils.makeUserChoose(clientManager)
            .addUserChoice(
                () -> gameView.setMainContentView(new MainMenuView(clientManager)),
                "client.cli.game.returnToMenu"
            ).apply();
    }
}
