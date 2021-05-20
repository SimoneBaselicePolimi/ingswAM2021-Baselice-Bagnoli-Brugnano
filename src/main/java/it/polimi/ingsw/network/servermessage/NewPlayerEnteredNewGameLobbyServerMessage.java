package it.polimi.ingsw.network.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;

import java.util.Set;

public class NewPlayerEnteredNewGameLobbyServerMessage extends LobbyStatusServerMessage {

    public final Player newPlayer;

    public NewPlayerEnteredNewGameLobbyServerMessage(
        @JsonProperty("newPlayer") Player newPlayer,
        @JsonProperty("playersInLobby") Set<Player> playersInLobby
    ) {
        super(playersInLobby);
        this.newPlayer = newPlayer;
    }

}
