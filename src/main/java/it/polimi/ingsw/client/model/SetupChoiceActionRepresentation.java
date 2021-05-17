package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class SetupChoiceActionRepresentation extends GameActionRepresentation {
    private final PlayerRepresentation player;
    private final Map<ResourceType,Integer> initialResources;

    public SetupChoiceActionRepresentation(PlayerRepresentation player, Map<ResourceType, Integer> initialResources) {
        this.player = player;
        this.initialResources = initialResources;
    }

    public PlayerRepresentation getPlayer() {
        return player;
    }

    public Map<ResourceType, Integer> getInitialResources() {
        return initialResources;
    }
}
