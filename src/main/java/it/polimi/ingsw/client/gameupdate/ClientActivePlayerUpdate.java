package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.server.model.Player;

public class ClientActivePlayerUpdate extends ClientGameUpdate {
    public final Player newActivePlayer;

    public ClientActivePlayerUpdate(Player newActivePlayer) {
        this.newActivePlayer = newActivePlayer;
    }
}
