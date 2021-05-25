package it.polimi.ingsw.client.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;

import java.util.Set;

public class InitialChoicesServerMessage extends GameUpdateServerMessage {

    @SerializeAsSetOfIds
    public final Set<ClientLeaderCardRepresentation> leaderCardsGivenToThePlayer;

    public final int numberOfStarResources;

    public InitialChoicesServerMessage(
        @JsonProperty("gameUpdates") Set<ServerGameUpdate> gameUpdates,
        @JsonProperty("leaderCardsGivenToThePlayer") Set<ClientLeaderCardRepresentation> leaderCardsGivenToThePlayer,
        @JsonProperty("numberOfStarResources") int numberOfStarResources
    ) {
        super(gameUpdates);
        this.leaderCardsGivenToThePlayer = leaderCardsGivenToThePlayer;
        this.numberOfStarResources = numberOfStarResources;
    }
}
