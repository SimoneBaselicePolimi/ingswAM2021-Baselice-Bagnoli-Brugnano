package it.polimi.ingsw.server.model.gamecontext;

import java.security.PrivilegedActionException;

public class GameContextCreationError extends Exception {

    public GameContextCreationError() {
    }

    public GameContextCreationError(String message) {
        super(message);
    }

    public GameContextCreationError(String message, Throwable cause) {
        super(message, cause);
    }

    public GameContextCreationError(Throwable cause) {
        super(cause);
    }

}
