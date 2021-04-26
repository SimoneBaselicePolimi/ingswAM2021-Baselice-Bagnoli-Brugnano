package it.polimi.ingsw.network.clientrequest;

import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.network.clientrequest.validator.MarketActionFetchColumnClientRequestValidator;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.Map;

public class MarketActionFetchColumnClientRequest extends ClientRequest {
    public final int column;

    public MarketActionFetchColumnClientRequest(
        Player player, int column) {
        super(player);
        this.column = column;
    }

    public Map<Player, ServerMessage> callHandler(GameState state) throws ResourceStorageRuleViolationException, NotEnoughResourcesException {
		return(state.handleRequestFetchColumnMarketAction(this));
	}

    @Override
    public ClientRequestValidator getValidator() {
        return new MarketActionFetchColumnClientRequestValidator();
    }

}
