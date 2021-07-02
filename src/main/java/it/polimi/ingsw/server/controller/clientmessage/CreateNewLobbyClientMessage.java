package it.polimi.ingsw.server.controller.clientmessage;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.server.controller.Client;

public class CreateNewLobbyClientMessage extends ClientMessage {

    public final int lobbySize;

    public CreateNewLobbyClientMessage(
        @JsonProperty("lobbySize") int lobbySize,
        @JacksonInject("client") Client client
    ) {
        super(client);
        this.lobbySize = lobbySize;
    }

}
