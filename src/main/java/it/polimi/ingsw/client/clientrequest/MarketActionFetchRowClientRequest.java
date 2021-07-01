package it.polimi.ingsw.client.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;

public class MarketActionFetchRowClientRequest extends ClientRequest {

    public final int row;

    public MarketActionFetchRowClientRequest(
        @JsonProperty("player") Player player,
        @JsonProperty("row") int row
    ) {
        super(player);
        this.row = row;
    }
}
