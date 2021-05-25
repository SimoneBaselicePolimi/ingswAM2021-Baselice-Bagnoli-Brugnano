package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;

public class ClientMainTurnInitialActionRepresentation extends ClientGameActionRepresentation {
    private final Player player;

    public ClientMainTurnInitialActionRepresentation(
        @JsonProperty("player") Player player
    ) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
