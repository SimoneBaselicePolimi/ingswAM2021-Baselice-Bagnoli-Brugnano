package it.polimi.ingsw.server.model.gameitems.cardstack;

import java.security.PrivilegedActionException;

public class CannotPushCardOnTopException extends Exception {

    public CannotPushCardOnTopException() {
    }

    public CannotPushCardOnTopException(String message) {
        super(message);
    }

    public CannotPushCardOnTopException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotPushCardOnTopException(Throwable cause) {
        super(cause);
    }
}
