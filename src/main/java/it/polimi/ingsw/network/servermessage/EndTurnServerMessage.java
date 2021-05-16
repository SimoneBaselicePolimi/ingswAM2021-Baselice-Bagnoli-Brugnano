package it.polimi.ingsw.network.servermessage;

import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;

import java.util.Set;

public class EndTurnServerMessage extends GameUpdateServerMessage {

    public EndTurnServerMessage(Set<ServerGameUpdate> gameUpdates) {
        super(gameUpdates);
    }
}
