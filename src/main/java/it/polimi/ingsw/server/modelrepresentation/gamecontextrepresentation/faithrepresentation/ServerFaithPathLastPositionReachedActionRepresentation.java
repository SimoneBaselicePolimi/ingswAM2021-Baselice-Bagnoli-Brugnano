package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;

public class ServerFaithPathLastPositionReachedActionRepresentation extends ServerGameActionRepresentation {

    public final ServerPlayerRepresentation player;

    public ServerFaithPathLastPositionReachedActionRepresentation(
        @JsonProperty("player") ServerPlayerRepresentation player
    ) {
        this.player = player;
    }
}
