package it.polimi.ingsw.server.controller.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.controller.clientrequest.validator.ActivateLeaderCardClientRequestValidator;
import it.polimi.ingsw.server.controller.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.utils.serialization.annotations.*;

import java.util.Map;

/**
 * Class representing the player's request to activate a leader card he holds in his hand
 */
public class ActivateLeaderCardClientRequest extends ClientRequest {

    @SerializeIdOnly
    public final LeaderCard leaderCardThePlayerWantsToActivate;

    /**
     * ActivateLeaderCardClientRequest constructor
     * @param player player who wants to activate a leader card
     * @param leaderCardThePlayerWantsToActivate chosen leader card to be activated
     */
    public ActivateLeaderCardClientRequest(
        @JsonProperty("player") Player player,
        @JsonProperty("leaderCardThePlayerWantsToActivate") LeaderCard leaderCardThePlayerWantsToActivate
    ) {
        super(player);
        this.leaderCardThePlayerWantsToActivate = leaderCardThePlayerWantsToActivate;
    }

    public Map<Player, ServerMessage> callHandler(GameState state) throws LeaderCardRequirementsNotSatisfiedException {
		return(state.handleRequestActivateLeaderAction(this));
	}

    @Override
    public ClientRequestValidator getValidator() {
        return new ActivateLeaderCardClientRequestValidator();
    }

}
