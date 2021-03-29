package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

public class ResourceNumberRequirement extends LeaderCardRequirement {
    ResourceType resourceType;
    int resourceNumber;

    public ResourceNumberRequirement(ResourceType resourceType, int resourceNumber) {
        this.resourceType = resourceType;
        this.resourceNumber = resourceNumber;
    }

    @Override
    public boolean checkRequirement(PlayerContext playerContext) {
        if (playerContext.getAllResources().containsKey(resourceType)){
            if (playerContext.getAllResources().get(resourceType) == resourceNumber)
                return true;
        }
        return false;
    }
}
