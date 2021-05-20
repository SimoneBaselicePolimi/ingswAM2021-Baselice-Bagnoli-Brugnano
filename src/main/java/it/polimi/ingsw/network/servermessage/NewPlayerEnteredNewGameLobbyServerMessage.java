package it.polimi.ingsw.network.servermessage;

import it.polimi.ingsw.server.model.Player;

import java.util.Set;

public class NewPlayerEnteredNewGameLobbyServerMessage extends LobbyStatusServerMessage {

    public final Player newPlayer;

    public NewPlayerEnteredNewGameLobbyServerMessage(
        Player newPlayer,
        Set<Player> playersInLobby
    ) {
        super(playersInLobby);
        this.newPlayer = newPlayer;
    }

}
