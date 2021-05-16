package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.Player;

public class ServerActivePlayerUpdate extends ServerGameUpdate {
    public final Player newActivePlayer;

    public ServerActivePlayerUpdate(Player newActivePlayer) {
        this.newActivePlayer = newActivePlayer;
    }
}
