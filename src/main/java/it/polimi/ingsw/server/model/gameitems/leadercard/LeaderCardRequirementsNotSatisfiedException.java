package it.polimi.ingsw.server.model.gameitems.leadercard;

import java.security.PrivilegedActionException;

/**
 *  Exception thrown if a player wants to activate a leader card but not all card requirements have been satisfied
 */
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
