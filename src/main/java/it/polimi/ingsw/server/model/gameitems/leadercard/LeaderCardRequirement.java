package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;

public abstract class LeaderCardRequirement {

    public abstract boolean checkRequirement(PlayerContext playerContext);

}
