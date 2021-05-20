package it.polimi.ingsw.network.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;

import java.util.Set;

public class LobbyStatusServerMessage extends ServerMessage {

    public final Set<Player> playersInLobby;

    public LobbyStatusServerMessage(
        @JsonProperty("playersInLobby") Set<Player> playersInLobby
    ) {
        this.playersInLobby = playersInLobby;
    }

}
