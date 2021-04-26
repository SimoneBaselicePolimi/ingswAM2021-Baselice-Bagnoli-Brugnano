package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.Player;

public class ActivePlayerUpdate extends GameUpdate {
    public final Player newActivePlayer;

    public ActivePlayerUpdate(Player newActivePlayer) {
        this.newActivePlayer = newActivePlayer;
    }
}
