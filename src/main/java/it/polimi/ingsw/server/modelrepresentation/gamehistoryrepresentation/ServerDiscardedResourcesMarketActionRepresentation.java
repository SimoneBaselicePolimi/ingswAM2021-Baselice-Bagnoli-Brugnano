package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

public class ServerDiscardedResourcesMarketActionRepresentation extends ServerGameActionRepresentation {

    public final ServerPlayerRepresentation player;
    public final int numberOfResourcesDiscarded;

    public ServerDiscardedResourcesMarketActionRepresentation(
        @JsonProperty("player") ServerPlayerRepresentation player,
        @JsonProperty("numberOfResourcesDiscarded") int numberOfResourcesDiscarded
    ) {
        this.player = player;
        this.numberOfResourcesDiscarded = numberOfResourcesDiscarded;
    }
}
