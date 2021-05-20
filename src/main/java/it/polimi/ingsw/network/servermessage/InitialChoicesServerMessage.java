package it.polimi.ingsw.network.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.utils.serialization.annotations.*;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;

import java.util.Set;

public class InitialChoicesServerMessage extends GameUpdateServerMessage{

    @SerializeAsSetOfIds
    public final Set<LeaderCard> leaderCardsGivenToThePlayer;

    public final int numberOfStarResources;

    public InitialChoicesServerMessage(
        @JsonProperty("gameUpdates") Set<ServerGameUpdate> gameUpdates,
        @JsonProperty("leaderCardsGivenToThePlayer") Set<LeaderCard> leaderCardsGivenToThePlayer,
        @JsonProperty("numberOfStarResources") int numberOfStarResources
    ) {
        super(gameUpdates);
        this.leaderCardsGivenToThePlayer = leaderCardsGivenToThePlayer;
        this.numberOfStarResources = numberOfStarResources;
    }
}
