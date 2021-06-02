package it.polimi.ingsw.client.clientmessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.client.clientrequest.ClientRequest;

@JsonTypeName("PlayerRequestClientMessage")
public class PlayerRequestClientMessage extends ClientMessage {

    public final it.polimi.ingsw.client.clientrequest.ClientRequest request;

    public PlayerRequestClientMessage(@JsonProperty("request") ClientRequest request) {
        this.request = request;
    }

}
