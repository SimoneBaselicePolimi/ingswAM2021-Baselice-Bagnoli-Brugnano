package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;

public class ServerDiscardedResourcesMarketActionRepresentation extends ServerGameActionRepresentation {

    public final Player player;
    public final int numberOfResourcesDiscarded;

    public ServerDiscardedResourcesMarketActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("numberOfResourcesDiscarded") int numberOfResourcesDiscarded
    ) {
        this.player = player;
        this.numberOfResourcesDiscarded = numberOfResourcesDiscarded;
    }
}
