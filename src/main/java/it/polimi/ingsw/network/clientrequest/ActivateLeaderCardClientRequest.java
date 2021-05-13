package it.polimi.ingsw.network.clientrequest;

import it.polimi.ingsw.network.clientrequest.validator.ActivateLeaderCardClientRequestValidator;
import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.network.servermessage.ServerMessage;
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

    @SerializeAsSetOfIds
    public final LeaderCard leaderCardThePlayerWantsToActivate;

    /**
     * ActivateLeaderCardClientRequest constructor
     * @param player player who wants to activate a leader card
     * @param leaderCardThePlayerWantsToActivate chosen leader card to be activated
     */
    public ActivateLeaderCardClientRequest(
        Player player,
        LeaderCard leaderCardThePlayerWantsToActivate
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
