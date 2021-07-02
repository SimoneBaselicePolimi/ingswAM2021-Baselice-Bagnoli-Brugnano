package it.polimi.ingsw.server.controller.clientmessage;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.server.controller.Client;

public class RegisterPlayerNameClientMessage extends ClientMessage {

    public final String playerName;

    public RegisterPlayerNameClientMessage(
        @JsonProperty("playerName") String playerName,
        @JacksonInject("client") Client client
    ) {
        super(client);
        this.playerName = playerName;
    }

}
