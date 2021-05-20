package it.polimi.ingsw.network.servermessage;

public class PlayerCanCreateNewLobbyServerMessage extends ServerMessage {

    public final int minLobbySize;
    public final int maxLobbySize;

    public PlayerCanCreateNewLobbyServerMessage(int minLobbySize, int maxLobbySize) {
        this.minLobbySize = minLobbySize;
        this.maxLobbySize = maxLobbySize;
    }

}
