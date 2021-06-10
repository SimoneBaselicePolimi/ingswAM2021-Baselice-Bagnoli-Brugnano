package it.polimi.ingsw.client.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;

public class EndTurnClientRequest extends ClientRequest {

    public EndTurnClientRequest(
        @JsonProperty("player") Player player
    ) {
        super(player);
    }

}
