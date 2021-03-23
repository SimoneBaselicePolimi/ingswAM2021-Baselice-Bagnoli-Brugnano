package it.polimi.ingsw.server.model.storage;

import java.security.PrivilegedActionException;

public class NotEnoughResourcesException extends Exception {

    public NotEnoughResourcesException() {
    }

    public NotEnoughResourcesException(String message) {
        super(message);
    }

    public NotEnoughResourcesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughResourcesException(Throwable cause) {
        super(cause);
    }
}
