package it.polimi.ingsw.client.clientrequest;

import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;

import java.util.Map;

public class CustomClientRequest extends ClientRequest {

    public CustomClientRequest(Player player) {
        super(player);
    }

}
