package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

public class ServerPostTurnFinalActionRepresentation extends ServerGameActionRepresentation {

    public final ServerPlayerRepresentation player;

    public ServerPostTurnFinalActionRepresentation(
        @JsonProperty("player") ServerPlayerRepresentation player
    ) {
        this.player = player;
    }

}
