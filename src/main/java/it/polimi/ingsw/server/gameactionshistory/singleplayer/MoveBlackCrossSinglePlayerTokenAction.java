package it.polimi.ingsw.server.gameactionshistory.singleplayer;

import it.polimi.ingsw.server.gameactionshistory.GameAction;

public class MoveBlackCrossSinglePlayerTokenAction extends GameAction {

    public final int steps;

    public MoveBlackCrossSinglePlayerTokenAction(int steps) {
        this.steps = steps;
    }

}
