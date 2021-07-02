package it.polimi.ingsw.client.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;

public class MarketActionFetchColumnClientRequest extends ClientRequest {

    public final int column;

    public MarketActionFetchColumnClientRequest(
        @JsonProperty("player") Player player,
        @JsonProperty("column") int column
    ) {
        super(player);
        this.column = column;
    }

}
