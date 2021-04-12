package it.polimi.ingsw.network.clientrequest;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.network.servermessage.ServerMessage;

import java.util.Map;

public class LeaderActionClientRequest extends ClientRequest {

    public LeaderActionClientRequest(Player player) {
        super(player);
    }

    public Map<Player, ServerMessage> callHandler(GameState state) {
		return(state.handleRequestLeaderAction(this));
	}

}
