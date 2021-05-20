package it.polimi.ingsw.network.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;

import java.util.List;
import java.util.Set;

public class NewPlayerEnteredNewGameLobbyServerMessage extends ServerMessage {

    public final Player newPlayer;
    public final List<Player> playersInLobby;

    public NewPlayerEnteredNewGameLobbyServerMessage(
        @JsonProperty("newPlayer") Player newPlayer,
        @JsonProperty("playersInLobby") List<Player> playersInLobby
    ) {
        this.newPlayer = newPlayer;
        this.playersInLobby = playersInLobby;
    }

}
