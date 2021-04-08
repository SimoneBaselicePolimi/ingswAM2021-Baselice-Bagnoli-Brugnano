package it.polimi.ingsw.server.model.gameitems.leadercard;

import java.security.PrivilegedActionException;

public class LeaderCardRequirementsNotSatisfied extends Exception {

    public LeaderCardRequirementsNotSatisfied() {
    }

    public LeaderCardRequirementsNotSatisfied(String message) {
        super(message);
    }

    public LeaderCardRequirementsNotSatisfied(String message, Throwable cause) {
        super(message, cause);
    }

    public LeaderCardRequirementsNotSatisfied(Throwable cause) {
        super(cause);
    }
}
