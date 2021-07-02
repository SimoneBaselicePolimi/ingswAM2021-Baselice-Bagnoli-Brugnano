package it.polimi.ingsw.client.clientmessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("CreateNewLobbyClientMessage")
public class CreateNewLobbyClientMessage extends ClientMessage {

    public final int lobbySize;

    public CreateNewLobbyClientMessage(
        @JsonProperty("lobbySize") int lobbySize
    ) {
        this.lobbySize = lobbySize;
    }

}
