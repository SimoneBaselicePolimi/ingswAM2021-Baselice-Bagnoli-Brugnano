package it.polimi.ingsw.client.cli;

import java.security.PrivilegedActionException;

public class UnexpectedServerMessage extends Exception {

    public UnexpectedServerMessage() {
    }

    public UnexpectedServerMessage(String message) {
        super(message);
    }

    public UnexpectedServerMessage(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedServerMessage(Throwable cause) {
        super(cause);
    }

}
