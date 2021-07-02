package it.polimi.ingsw.server.gameactionshistory.singleplayer;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.gameactionshistory.GameAction;

public class FaithPathMoveBlackCrossAction extends GameAction {

    public final int steps;

    public FaithPathMoveBlackCrossAction(
        @JsonProperty("steps") int steps
    ) {
        this.steps = steps;
    }

}
