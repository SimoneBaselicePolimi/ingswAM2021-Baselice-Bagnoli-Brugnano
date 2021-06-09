package it.polimi.ingsw.client.clientrequest;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;

/**
 * Class representing the player's request to activate a leader card he holds in his hand
 */
public class ActivateLeaderCardClientRequest extends ClientRequest {

    @SerializeAsSetOfIds
    public final ClientLeaderCardRepresentation leaderCardThePlayerWantsToActivate;

    /**
     * ActivateLeaderCardClientRequest constructor
     * @param player player who wants to activate a leader card
     * @param leaderCardThePlayerWantsToActivate chosen leader card to be activated
     */
    public ActivateLeaderCardClientRequest(
        Player player,
        ClientLeaderCardRepresentation leaderCardThePlayerWantsToActivate
    ) {
        super(player);
        this.leaderCardThePlayerWantsToActivate = leaderCardThePlayerWantsToActivate;
    }

}
