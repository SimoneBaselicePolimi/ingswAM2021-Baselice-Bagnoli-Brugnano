package it.polimi.ingsw.server.controller.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.controller.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;

import java.util.Map;

public class EndTurnClientRequest extends ClientRequest {

    public EndTurnClientRequest(
        @JsonProperty("player") Player player
    ) {
        super(player);
    }

    public Map<Player, ServerMessage> callHandler(GameState state) {
		return(state.handleRequestEndTurn(this));
	}

    @Override
    public ClientRequestValidator getValidator() {
        return null;
    }

}
