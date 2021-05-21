package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

public class ServerMainTurnInitialActionRepresentation extends ServerGameActionRepresentation {

    public final ServerPlayerRepresentation player;

    public ServerMainTurnInitialActionRepresentation(
        @JsonProperty("player") ServerPlayerRepresentation player
    ) {
        this.player = player;
    }

}
