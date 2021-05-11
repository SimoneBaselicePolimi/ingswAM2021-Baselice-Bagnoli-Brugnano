package it.polimi.ingsw.network.servermessage;

import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;

import java.util.Set;

public class EndTurnServerMessage extends GameUpdateServerMessage {

    public EndTurnServerMessage(Set<GameUpdate> gameUpdates) {
        super(gameUpdates);
    }
}
