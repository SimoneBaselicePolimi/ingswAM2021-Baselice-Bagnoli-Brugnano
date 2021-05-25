package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;

public class ClientDiscardedResourcesMarketActionRepresentation extends ClientGameActionRepresentation {

    private final Player player;
    private final int numberOfResourcesDiscarded;

    public ClientDiscardedResourcesMarketActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("numberOfResourcesDiscarded") int numberOfResourcesDiscarded
    ) {
        this.player = player;
        this.numberOfResourcesDiscarded = numberOfResourcesDiscarded;
    }

    public Player getPlayer() {
        return player;
    }

    public int getNumberOfResourcesDiscarded() {
        return numberOfResourcesDiscarded;
    }
}
