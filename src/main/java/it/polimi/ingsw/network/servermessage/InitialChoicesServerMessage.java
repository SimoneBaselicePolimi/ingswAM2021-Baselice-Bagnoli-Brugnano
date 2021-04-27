package it.polimi.ingsw.network.servermessage;

import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;


import java.util.Set;

public class InitialChoicesServerMessage extends ServerMessage{

    @SerializeAsSetOfIds
    public final Set<LeaderCard> leaderCardsGivenToThePlayer;

    public final int numberOfStarResources;

    public InitialChoicesServerMessage(Set<LeaderCard> leaderCardsGivenToThePlayer, int numberOfStarResources) {
        this.leaderCardsGivenToThePlayer = leaderCardsGivenToThePlayer;
        this.numberOfStarResources = numberOfStarResources;
    }
}
