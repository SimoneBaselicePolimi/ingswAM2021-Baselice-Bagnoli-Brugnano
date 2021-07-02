package it.polimi.ingsw.server.gameactionshistory.singleplayer;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.gameactionshistory.GameAction;

public class MoveBlackCrossSinglePlayerTokenAction extends GameAction {

    public final int steps;

    public MoveBlackCrossSinglePlayerTokenAction(
        @JsonProperty("steps") int steps
    ) {
        this.steps = steps;
    }

}
