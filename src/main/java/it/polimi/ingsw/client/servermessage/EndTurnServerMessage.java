package it.polimi.ingsw.client.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gameupdate.ClientGameUpdate;

import java.util.Set;

public class EndTurnServerMessage extends GameUpdateServerMessage {

    public EndTurnServerMessage(
        @JsonProperty("gameUpdates") Set<ClientGameUpdate> gameUpdates
    ) {
        super(gameUpdates);
    }
}
