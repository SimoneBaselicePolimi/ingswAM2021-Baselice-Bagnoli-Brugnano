package it.polimi.ingsw.server.model.gameitems.leadercard;

import java.security.PrivilegedActionException;

public class LeaderCardRequirementsNotSatisfiedException extends Exception {

    public LeaderCardRequirementsNotSatisfiedException() {
    }

    public LeaderCardRequirementsNotSatisfiedException(String message) {
        super(message);
    }

    public LeaderCardRequirementsNotSatisfiedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LeaderCardRequirementsNotSatisfiedException(Throwable cause) {
        super(cause);
    }
}
