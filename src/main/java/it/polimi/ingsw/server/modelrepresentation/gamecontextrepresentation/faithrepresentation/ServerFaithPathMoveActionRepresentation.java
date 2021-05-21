package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;

public class ServerFaithPathMoveActionRepresentation extends ServerGameActionRepresentation {

    public final ServerPlayerRepresentation player;
    public final int steps;

    public ServerFaithPathMoveActionRepresentation(
        @JsonProperty("player") ServerPlayerRepresentation player,
        @JsonProperty("steps") int steps
    ) {
        this.player = player;
        this.steps = steps;
    }

}
