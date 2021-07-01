package it.polimi.ingsw.client.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

/**
 * Class representing the player's request to activate a leader card he holds in his hand
 */
public class ActivateLeaderCardClientRequest extends ClientRequest {

    @SerializeIdOnly
    public final ClientLeaderCardRepresentation leaderCardThePlayerWantsToActivate;

    /**
     * ActivateLeaderCardClientRequest constructor
     * @param player player who wants to activate a leader card
     * @param leaderCardThePlayerWantsToActivate chosen leader card to be activated
     */
    public ActivateLeaderCardClientRequest(
        @JsonProperty("player") Player player,
        @JsonProperty("leaderCardThePlayerWantsToActivate") ClientLeaderCardRepresentation leaderCardThePlayerWantsToActivate
    ) {
        super(player);
        this.leaderCardThePlayerWantsToActivate = leaderCardThePlayerWantsToActivate;
    }

}
