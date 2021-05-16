package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gamehistory.GameAction;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class ObtainedResourcesMarketActionRepresentation extends GameAction {
    private final PlayerRepresentation player;
    private final Map<ResourceType, Integer> resourcesObtained;

    public ObtainedResourcesMarketActionRepresentation(PlayerRepresentation player, Map<ResourceType, Integer> resourcesObtained) {
        this.player = player;
        this.resourcesObtained = resourcesObtained;
    }

    public PlayerRepresentation getPlayer() {
        return player;
    }

    public Map<ResourceType, Integer> getResourcesObtained() {
        return resourcesObtained;
    }
}
