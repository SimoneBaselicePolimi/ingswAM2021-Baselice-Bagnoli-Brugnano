package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;

public class ServerPostTurnFinalActionRepresentation extends ServerGameActionRepresentation {

    public final Player player;

    public ServerPostTurnFinalActionRepresentation(
        @JsonProperty("player") Player player
    ) {
        this.player = player;
    }

}
