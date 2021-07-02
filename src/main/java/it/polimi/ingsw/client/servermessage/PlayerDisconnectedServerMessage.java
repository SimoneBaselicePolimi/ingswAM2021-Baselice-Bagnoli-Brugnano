package it.polimi.ingsw.client.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class PlayerDisconnectedServerMessage extends ServerMessage{

    @SerializeIdOnly
    public final Player player;

    public PlayerDisconnectedServerMessage(
        @JsonProperty("player") Player player
    ) {
        this.player = player;
    }

}
