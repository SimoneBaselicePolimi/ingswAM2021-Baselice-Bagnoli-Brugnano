package it.polimi.ingsw.network.servermessage;

import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.utils.serialization.annotations.*;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;

import java.util.Set;

public class InitialChoicesServerMessage extends GameUpdateServerMessage{

    @SerializeAsSetOfIds
    public final Set<LeaderCard> leaderCardsGivenToThePlayer;

    public final int numberOfStarResources;

    public InitialChoicesServerMessage(
        Set<ServerGameUpdate> gameUpdates,
        Set<LeaderCard> leaderCardsGivenToThePlayer,
        int numberOfStarResources
    ) {
        super(gameUpdates);
        this.leaderCardsGivenToThePlayer = leaderCardsGivenToThePlayer;
        this.numberOfStarResources = numberOfStarResources;
    }
}
