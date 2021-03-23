package it.polimi.ingsw.network.clientrequest;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.network.servermessage.ServerMessage;

import java.util.Map;

public class ClientRequestProductionAction extends ClientRequest {

	public Map<Player, ServerMessage> callHandler(GameState state) {
		return null;
	}

}
