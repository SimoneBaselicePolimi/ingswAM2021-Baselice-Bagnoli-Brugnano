package it.polimi.ingsw.client.clientmessage;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "clientMessageType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PlayerRequestClientMessage.class, name = "PlayerRequestClientMessage"),
    @JsonSubTypes.Type(value = CreateNewLobbyClientMessage.class, name = "CreateNewLobbyClientMessage"),
    @JsonSubTypes.Type(value = RegisterPlayerNameClientMessage.class, name = "RegisterPlayerNameClientMessage")
})
public class ClientMessage {

}
