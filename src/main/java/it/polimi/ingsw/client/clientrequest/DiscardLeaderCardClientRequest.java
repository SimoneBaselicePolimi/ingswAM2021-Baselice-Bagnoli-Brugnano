package it.polimi.ingsw.client.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class DiscardLeaderCardClientRequest extends ClientRequest {

    @SerializeIdOnly
    public final ClientLeaderCardRepresentation leaderCardThePlayerWantsToDiscard;

    public DiscardLeaderCardClientRequest(
        @JsonProperty("player") Player player,
        @JsonProperty("leaderCardThePlayerWantsToDiscard") ClientLeaderCardRepresentation leaderCardThePlayerWantsToDiscard
    ) {
        super(player);
        this.leaderCardThePlayerWantsToDiscard = leaderCardThePlayerWantsToDiscard;
    }

}
