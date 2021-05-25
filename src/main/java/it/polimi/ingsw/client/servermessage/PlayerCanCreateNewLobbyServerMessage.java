package it.polimi.ingsw.client.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerCanCreateNewLobbyServerMessage extends ServerMessage {

    public final boolean singlePlayerEnabled;
    public final int maxLobbySize;

    public PlayerCanCreateNewLobbyServerMessage(
        @JsonProperty("singlePlayerEnabled") boolean singlePlayerEnabled,
        @JsonProperty("maxLobbySize") int maxLobbySize
    ) {
        this.singlePlayerEnabled = singlePlayerEnabled;
        this.maxLobbySize = maxLobbySize;
    }

}
