package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

public class ResourceNumberRequirement extends LeaderCardRequirement {

    public ResourceNumberRequirement(ResourceType resourceType, int resourceNumber) {

    }

    @Override
    public boolean checkRequirement(PlayerContext playerContext) {
        return false;
    }
}
