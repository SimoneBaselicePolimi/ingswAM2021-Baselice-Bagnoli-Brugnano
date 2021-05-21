package it.polimi.ingsw.server.controller.clientmessage;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.network.clientrequest.ClientRequest;
import it.polimi.ingsw.server.controller.Client;

public class PlayerRequestClientMessage extends ClientMessage {

    public final ClientRequest request;

    public PlayerRequestClientMessage(
        @JsonProperty("request") ClientRequest request,
        @JacksonInject("client") Client client
    ) {
        super(client);
        this.request = request;
    }

}
