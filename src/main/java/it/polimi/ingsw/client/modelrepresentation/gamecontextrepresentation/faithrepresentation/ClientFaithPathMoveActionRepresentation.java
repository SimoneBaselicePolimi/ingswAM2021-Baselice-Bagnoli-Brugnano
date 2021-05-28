package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.server.model.Player;

public class ClientFaithPathMoveActionRepresentation extends ClientRepresentation {

    private final Player player;
    private final int steps;

    public ClientFaithPathMoveActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("steps") int steps
    ) {
        this.player = player;
        this.steps = steps;
    }

    public Player getPlayer() {
        return player;
    }

    public int getSteps() {
        return steps;
    }
}
