package it.polimi.ingsw.server.gameactionshistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class PostTurnFinalAction extends GameAction{

    @SerializeIdOnly
    private final Player player;

    public PostTurnFinalAction(@JsonProperty("player") Player player) {
        this.player = player;
    }

}
