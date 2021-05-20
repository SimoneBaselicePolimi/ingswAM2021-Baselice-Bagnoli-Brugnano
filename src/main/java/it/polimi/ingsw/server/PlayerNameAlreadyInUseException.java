package it.polimi.ingsw.server;

import java.security.PrivilegedActionException;

public class PlayerNameAlreadyInUseException extends Exception {

    public PlayerNameAlreadyInUseException() {
    }

    public PlayerNameAlreadyInUseException(String message) {
        super(message);
    }

    public PlayerNameAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerNameAlreadyInUseException(Throwable cause) {
        super(cause);
    }

}
