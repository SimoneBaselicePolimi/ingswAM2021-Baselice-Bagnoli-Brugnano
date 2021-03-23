package it.polimi.ingsw.network.clientrequest;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.network.servermessage.ServerMessage;

import java.util.Map;

public class ClientRequestManageResourcesFromMarket extends ClientRequest {

	public Map<Player, ServerMessage> callHandler(GameState state) {
		return null;
	}

}
