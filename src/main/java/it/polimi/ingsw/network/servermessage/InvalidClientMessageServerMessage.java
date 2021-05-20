package it.polimi.ingsw.network.servermessage;

public class InvalidClientMessageServerMessage extends ServerMessage {

    public final String errorMessage;

    public InvalidClientMessageServerMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
