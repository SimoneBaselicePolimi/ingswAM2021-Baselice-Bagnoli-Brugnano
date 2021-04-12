package it.polimi.ingsw.network.servermessage;

import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServerMessageWithGameUpdates {

    public final Set<GameUpdate> gameUpdates;

    public ServerMessageWithGameUpdates(Set<GameUpdate> gameUpdates) {
        this.gameUpdates = new HashSet<>(gameUpdates);
    }

}
