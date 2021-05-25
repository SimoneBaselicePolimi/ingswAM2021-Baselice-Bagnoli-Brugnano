package it.polimi.ingsw.network.clientrequest;

import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.network.clientrequest.validator.MarketActionFetchRowClientRequestValidator;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.Map;

public class MarketActionFetchRowClientRequest extends ClientRequest {
    public final int row;

    public MarketActionFetchRowClientRequest(Player player, int row) {
        super(player);
        this.row = row;
    }

    public Map<Player, ServerMessage> callHandler(GameState state)
        throws ResourceStorageRuleViolationException, NotEnoughResourcesException {
		return(state.handleRequestFetchRowMarketAction(this));
	}

    @Override
    public ClientRequestValidator getValidator() {
        return new MarketActionFetchRowClientRequestValidator();
    }
}
