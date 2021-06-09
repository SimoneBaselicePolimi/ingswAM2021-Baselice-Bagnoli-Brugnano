package it.polimi.ingsw.client.clientrequest;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;

public class DiscardLeaderCardClientRequest extends ClientRequest {

    @SerializeAsSetOfIds
    public final ClientLeaderCardRepresentation leaderCardThePlayerWantsToDiscard;

    public DiscardLeaderCardClientRequest(
        Player player,
        ClientLeaderCardRepresentation leaderCardThePlayerWantsToDiscard
    ) {
        super(player);
        this.leaderCardThePlayerWantsToDiscard = leaderCardThePlayerWantsToDiscard;
    }

}
