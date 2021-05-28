package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.server.model.Player;

public class ClientFaithPathLastPositionReachedActionRepresentation extends ClientRepresentation {

    private final Player player;

    public ClientFaithPathLastPositionReachedActionRepresentation(
        @JsonProperty("player") Player player
    ) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
