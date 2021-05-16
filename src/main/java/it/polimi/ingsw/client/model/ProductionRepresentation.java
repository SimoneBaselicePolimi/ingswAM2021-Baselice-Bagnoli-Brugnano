package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class ProductionRepresentation extends Representation{

    /**
     * Production cost made of specific type and number of Resources
     */
    private final Map<ResourceType, Integer> resourceCost;

    /**
     * Production reward made of specific type and number of Resources
     */
    private final Map<ResourceType, Integer> resourceReward;

    /**
     * Production cost made of a generic type of Resource (Player can choose), in a fixed quantity
     */
    private final int starResourceCost;

    /**
     * Production reward made of a generic type of Resource (Player can choose), in a fixed quantity
     */
    private final int starResourceReward;

    /**
     * Production reward made of a fixed number of Faith Points
     */
    private final int faithReward;

    public ProductionRepresentation(
        Map<ResourceType, Integer> resourceCost,
        Map<ResourceType, Integer> resourceReward,
        int starResourceCost,
        int starResourceReward,
        int faithReward
    ) {
        this.resourceCost = resourceCost;
        this.resourceReward = resourceReward;
        this.starResourceCost = starResourceCost;
        this.starResourceReward = starResourceReward;
        this.faithReward = faithReward;
    }

    public Map<ResourceType, Integer> getResourceCost() {
        return resourceCost;
    }

    public Map<ResourceType, Integer> getResourceReward() {
        return resourceReward;
    }

    public int getStarResourceCost() {
        return starResourceCost;
    }

    public int getStarResourceReward() {
        return starResourceReward;
    }

    public int getFaithReward() {
        return faithReward;
    }
}
