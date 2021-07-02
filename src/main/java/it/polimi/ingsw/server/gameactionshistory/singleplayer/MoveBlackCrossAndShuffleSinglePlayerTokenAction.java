package it.polimi.ingsw.server.gameactionshistory.singleplayer;

import it.polimi.ingsw.server.gameactionshistory.GameAction;

public class MoveBlackCrossAndShuffleSinglePlayerTokenAction extends GameAction {

    final int steps;

    public MoveBlackCrossAndShuffleSinglePlayerTokenAction(int steps) {
        this.steps = steps;
    }

}
