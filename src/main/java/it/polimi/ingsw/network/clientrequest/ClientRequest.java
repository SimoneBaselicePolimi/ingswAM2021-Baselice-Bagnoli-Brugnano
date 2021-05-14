package it.polimi.ingsw.network.clientrequest;

import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.Map;

//TODO rename ClientRequest in PlayerRequest
public abstract class ClientRequest {

	public final Player player;

	public ClientRequest(Player player) {
		this.player = player;
	}

	public Map<Player, ServerMessage> callHandler(GameState state) throws ResourceStorageRuleViolationException, LeaderCardRequirementsNotSatisfiedException, ForbiddenPushOnTopException, NotEnoughResourcesException { return null;}

	public abstract ClientRequestValidator getValidator();

}
