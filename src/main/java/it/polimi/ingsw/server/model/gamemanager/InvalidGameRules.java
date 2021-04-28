package it.polimi.ingsw.server.model.gamemanager;

import java.security.PrivilegedActionException;

public class InvalidGameRules extends Exception {

    public InvalidGameRules() {
    }

    public InvalidGameRules(String message) {
        super(message);
    }

    public InvalidGameRules(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidGameRules(Throwable cause) {
        super(cause);
    }

}
