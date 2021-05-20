package it.polimi.ingsw.network.servermessage;

import it.polimi.ingsw.server.model.Player;

import java.util.Set;

public class LobbyStatusServerMessage extends ServerMessage {

    public final Set<Player> playersInLobby;

    public LobbyStatusServerMessage(Set<Player> playersInLobby) {
        this.playersInLobby = playersInLobby;
    }

}
