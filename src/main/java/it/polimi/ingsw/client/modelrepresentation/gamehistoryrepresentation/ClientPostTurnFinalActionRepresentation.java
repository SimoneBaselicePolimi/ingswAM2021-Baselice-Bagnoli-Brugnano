package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;

public class ClientPostTurnFinalActionRepresentation extends ClientGameActionRepresentation {
    private final ClientPlayerRepresentation player;

    public ClientPostTurnFinalActionRepresentation(
        @JsonProperty("player") ClientPlayerRepresentation player
    ) {
        this.player = player;
    }

    public ClientPlayerRepresentation getPlayer() {
        return player;
    }
}
