package it.polimi.ingsw.server.model.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;

public class ServerActivePlayerUpdate extends ServerGameUpdate {
    public final Player newActivePlayer;

    public ServerActivePlayerUpdate(
        @JsonProperty("newActivePlayer") Player newActivePlayer
    ) {
        this.newActivePlayer = newActivePlayer;
    }
}
