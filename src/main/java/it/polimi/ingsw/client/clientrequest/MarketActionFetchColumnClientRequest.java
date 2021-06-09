package it.polimi.ingsw.client.clientrequest;

import it.polimi.ingsw.server.model.Player;

public class MarketActionFetchColumnClientRequest extends ClientRequest {
    public final int column;

    public MarketActionFetchColumnClientRequest(Player player, int column) {
        super(player);
        this.column = column;
    }

}
