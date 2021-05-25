package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.ClientGameActionRepresentation;
import it.polimi.ingsw.server.model.Player;

public class ClientFaithPathLastPositionReachedActionRepresentation extends ClientGameActionRepresentation {

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
