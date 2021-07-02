package it.polimi.ingsw.server.gameactionshistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class FaithPathMoveAction extends GameAction {

    @SerializeIdOnly
    private final Player player;

    @JsonProperty("steps")
    private final int steps;

    public FaithPathMoveAction(
        @JsonProperty("player") Player player,
        @JsonProperty("steps") int steps
    ) {
        this.player = player;
        this.steps = steps;
    }

}
