package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;

public class ClientMainTurnInitialActionRepresentation extends ClientGameActionRepresentation {
    private final ClientPlayerRepresentation player;

    public ClientMainTurnInitialActionRepresentation(
        @JsonProperty("player") ClientPlayerRepresentation player
    ) {
        this.player = player;
    }

    public ClientPlayerRepresentation getPlayer() {
        return player;
    }
}
