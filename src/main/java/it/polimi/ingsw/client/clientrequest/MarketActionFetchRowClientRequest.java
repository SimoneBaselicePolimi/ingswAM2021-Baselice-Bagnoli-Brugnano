package it.polimi.ingsw.client.clientrequest;

import it.polimi.ingsw.server.model.Player;

public class MarketActionFetchRowClientRequest extends ClientRequest {
    public final int row;

    public MarketActionFetchRowClientRequest(Player player, int row) {
        super(player);
        this.row = row;
    }
}
