package it.polimi.ingsw.server.controller.clientmessage;

import com.fasterxml.jackson.annotation.*;
import it.polimi.ingsw.server.controller.Client;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "clientMessageType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PlayerRequestClientMessage.class, name = "PlayerRequestClientMessage"),
    @JsonSubTypes.Type(value = CreateNewLobbyClientMessage.class, name = "CreateNewLobbyClientMessage"),
    @JsonSubTypes.Type(value = RegisterPlayerNameClientMessage.class, name = "RegisterPlayerNameClientMessage"),
    @JsonSubTypes.Type(value = GetInitialGameRepresentationClientMessage.class, name = "GetInitialGameRepresentationClientMessage"),
    @JsonSubTypes.Type(value = ReadyToStartGameClientMessage.class, name = "ReadyToStartGameClientMessage")
})
public class ClientMessage {

    @JsonIgnore
    public final Client client;

    public ClientMessage(@JacksonInject("client") Client client) {
        this.client = client;
    }


}
