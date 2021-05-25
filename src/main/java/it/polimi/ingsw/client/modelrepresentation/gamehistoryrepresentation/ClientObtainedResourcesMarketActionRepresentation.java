package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class ClientObtainedResourcesMarketActionRepresentation extends ClientGameActionRepresentation {
    private final Player player;
    private final Map<ResourceType, Integer> resourcesObtained;

    public ClientObtainedResourcesMarketActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("resourcesObtained")Map<ResourceType, Integer> resourcesObtained
    ) {
        this.player = player;
        this.resourcesObtained = resourcesObtained;
    }

    public Player getPlayer() {
        return player;
    }

    public Map<ResourceType, Integer> getResourcesObtained() {
        return resourcesObtained;
    }
}
