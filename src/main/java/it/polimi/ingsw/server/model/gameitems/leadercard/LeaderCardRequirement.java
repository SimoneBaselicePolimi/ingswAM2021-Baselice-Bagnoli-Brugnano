package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerLeaderCardRequirementRepresentation;

/**
 * This class models a requirement necessary to activate a leader card
 */
public abstract class LeaderCardRequirement implements Representable<ServerLeaderCardRequirementRepresentation> {

    public abstract boolean checkRequirement(PlayerContext playerContext);

}
