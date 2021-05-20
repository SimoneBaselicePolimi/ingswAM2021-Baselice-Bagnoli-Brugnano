package it.polimi.ingsw.network.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;

import java.util.Set;

public class EndTurnServerMessage extends GameUpdateServerMessage {

    public EndTurnServerMessage(
        @JsonProperty("gameUpdates") Set<ServerGameUpdate> gameUpdates
    ) {
        super(gameUpdates);
    }
}
