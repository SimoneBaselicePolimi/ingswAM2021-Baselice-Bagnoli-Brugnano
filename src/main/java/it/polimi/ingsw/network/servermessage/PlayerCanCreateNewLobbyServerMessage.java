package it.polimi.ingsw.network.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerCanCreateNewLobbyServerMessage extends ServerMessage {

    public final int minLobbySize;
    public final int maxLobbySize;

    public PlayerCanCreateNewLobbyServerMessage(
        @JsonProperty("minLobbySize") int minLobbySize,
        @JsonProperty("maxLobbySize") int maxLobbySize
    ) {
        this.minLobbySize = minLobbySize;
        this.maxLobbySize = maxLobbySize;
    }

}
