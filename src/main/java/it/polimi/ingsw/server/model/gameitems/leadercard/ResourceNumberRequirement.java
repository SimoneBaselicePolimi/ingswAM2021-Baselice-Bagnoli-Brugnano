package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

/**
 * request for a specific number of resources in the player's storages
 */
public class ResourceNumberRequirement extends LeaderCardRequirement {
    ResourceType resourceType;
    int resourceNumber;

    /**
     * ResourceNumberRequirement constructor
     * @param resourceType
     * @param resourceNumber
     */
    public ResourceNumberRequirement(ResourceType resourceType, int resourceNumber) {
        this.resourceType = resourceType;
        this.resourceNumber = resourceNumber;
    }

    /**
     * @param playerContext
     * @return true if the player has the type and number of resources to activate the card
     */
    @Override
    public boolean checkRequirement(PlayerContext playerContext) {
        if (playerContext.getAllResources().containsKey(resourceType)){
            if (playerContext.getAllResources().get(resourceType) == resourceNumber)
                return true;
        }
        return false;
    }
}
