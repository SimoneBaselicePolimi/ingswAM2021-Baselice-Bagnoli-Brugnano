package it.polimi.ingsw.server.gameactionshistory.singleplayer;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.gameactionshistory.GameAction;

public class MoveBlackCrossAndShuffleSinglePlayerTokenAction extends GameAction {

    public final int steps;

    public MoveBlackCrossAndShuffleSinglePlayerTokenAction(
        @JsonProperty("steps") int steps
    ) {
        this.steps = steps;
    }

}
