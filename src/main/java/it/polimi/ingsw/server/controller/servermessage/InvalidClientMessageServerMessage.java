package it.polimi.ingsw.server.controller.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvalidClientMessageServerMessage extends ServerMessage {

    public final String errorMessage;

    public InvalidClientMessageServerMessage(
        @JsonProperty("errorMessage")String errorMessage
    ) {
        this.errorMessage = errorMessage;
    }

}
