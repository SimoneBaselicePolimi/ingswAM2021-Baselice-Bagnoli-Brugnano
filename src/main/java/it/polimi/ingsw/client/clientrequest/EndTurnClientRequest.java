package it.polimi.ingsw.client.clientrequest;

import it.polimi.ingsw.server.model.Player;

public class EndTurnClientRequest extends ClientRequest {

    public EndTurnClientRequest(Player player) {
        super(player);
    }

}
