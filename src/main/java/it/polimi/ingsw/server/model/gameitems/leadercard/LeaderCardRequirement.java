package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;

/**
 * This class implements the requirements necessary to activate a leader card
 */
public abstract class LeaderCardRequirement {

    public abstract boolean checkRequirement(PlayerContext playerContext);

}
