package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class ClientObtainedResourcesMarketActionRepresentation extends ClientGameActionRepresentation {
    private final ClientPlayerRepresentation player;
    private final Map<ResourceType, Integer> resourcesObtained;

    public ClientObtainedResourcesMarketActionRepresentation(ClientPlayerRepresentation player, Map<ResourceType, Integer> resourcesObtained) {
        this.player = player;
        this.resourcesObtained = resourcesObtained;
    }

    public ClientPlayerRepresentation getPlayer() {
        return player;
    }

    public Map<ResourceType, Integer> getResourcesObtained() {
        return resourcesObtained;
    }
}
