package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;

public class ServerFaithPathMoveActionRepresentation extends ServerGameActionRepresentation {

    public final Player player;
    public final int steps;

    public ServerFaithPathMoveActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("steps") int steps
    ) {
        this.player = player;
        this.steps = steps;
    }

}
