package it.polimi.ingsw.network.servermessage;

public class InvalidRequestServerMessage extends ServerMessage {

    public final String errorMessage;

    public InvalidRequestServerMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
