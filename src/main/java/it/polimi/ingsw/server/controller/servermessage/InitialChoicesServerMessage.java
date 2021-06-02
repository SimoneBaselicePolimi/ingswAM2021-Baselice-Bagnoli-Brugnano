package it.polimi.ingsw.server.controller.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.utils.serialization.annotations.*;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;

import java.util.Set;

public class InitialChoicesServerMessage extends GameUpdateServerMessage{

    @SerializeAsSetOfIds
    public final Set<LeaderCard> leaderCardsGivenToThePlayer;

    public final int numberOfLeaderCardsToKeep;
    public final int numberOfStarResources;

    public InitialChoicesServerMessage(
        @JsonProperty("gameUpdates") Set<ServerGameUpdate> gameUpdates,
        @JsonProperty("leaderCardsGivenToThePlayer") Set<LeaderCard> leaderCardsGivenToThePlayer,
        @JsonProperty("numberOfLeaderCardsToKeep") int numberOfLeaderCardsToKeep,
        @JsonProperty("numberOfStarResources") int numberOfStarResources
    ) {
        super(gameUpdates);
        this.leaderCardsGivenToThePlayer = leaderCardsGivenToThePlayer;
        this.numberOfLeaderCardsToKeep = numberOfLeaderCardsToKeep;
        this.numberOfStarResources = numberOfStarResources;
    }
}
