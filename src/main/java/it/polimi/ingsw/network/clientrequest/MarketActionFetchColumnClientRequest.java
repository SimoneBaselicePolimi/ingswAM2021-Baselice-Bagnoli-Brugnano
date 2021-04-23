package it.polimi.ingsw.network.clientrequest;

import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.network.servermessage.ServerMessage;

import java.util.Map;

public class MarketActionFetchColumnClientRequest extends ClientRequest {
    public final int column;

    public MarketActionFetchColumnClientRequest(
        Player player, int column) {
        super(player);
        this.column = column;
    }

    public Map<Player, ServerMessage> callHandler(GameState state) {
		return(state.handleRequestFetchColumnMarketAction(this));
	}

    @Override
    public ClientRequestValidator getValidator() {
        return null;
    }

}
