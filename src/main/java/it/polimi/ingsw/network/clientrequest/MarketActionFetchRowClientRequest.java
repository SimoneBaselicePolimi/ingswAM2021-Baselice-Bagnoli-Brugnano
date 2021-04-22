package it.polimi.ingsw.network.clientrequest;

import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;

import java.util.Map;

public class MarketActionFetchRowClientRequest extends ClientRequest {
    public final int row;

    public MarketActionFetchRowClientRequest(
        Player player, int row) {
        super(player);
        this.row = row;
    }

    public Map<Player, ServerMessage> callHandler(GameState state) {
		return(state.handleRequestMarketAction(this));
	}

    @Override
    public ClientRequestValidator getValidator() {
        return null;
    }
}
