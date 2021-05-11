package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

/**
 * This class represent the request for a specific number of resources in the player's storages
 */
public class ResourceNumberRequirement extends LeaderCardRequirement {
    public final ResourceType resourceType;
    public final int resourceNumber;

    /**
     * ResourceNumberRequirement constructor
     * @param resourceType type of resource required
     * @param resourceNumber number of resource required
     */
    public ResourceNumberRequirement(ResourceType resourceType, int resourceNumber) {
        this.resourceType = resourceType;
        this.resourceNumber = resourceNumber;
    }

    /**
     * Method to verify that the player has the necessary resources (type and number) to activate a leader card
     * @param playerContext reference to the single player
     * @return true if the player has the type and number of resources to activate the card
     */
    @Override
    public boolean checkRequirement(PlayerContext playerContext) {
        return playerContext.getAllResources().containsKey(resourceType) && playerContext.getAllResources().get(resourceType) >= resourceNumber;
    }
}