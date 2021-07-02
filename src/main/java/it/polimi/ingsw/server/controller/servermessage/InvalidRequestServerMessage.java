package it.polimi.ingsw.server.controller.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvalidRequestServerMessage extends ServerMessage {

    public final String errorMessage;

    public InvalidRequestServerMessage(
        @JsonProperty("errorMessage") String errorMessage
    ) {
        this.errorMessage = errorMessage;
    }

}
