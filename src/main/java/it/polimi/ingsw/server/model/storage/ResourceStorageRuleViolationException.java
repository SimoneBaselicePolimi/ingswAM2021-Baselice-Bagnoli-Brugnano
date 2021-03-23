package it.polimi.ingsw.server.model.storage;

import java.security.PrivilegedActionException;

public class ResourceStorageRuleViolationException extends Exception {

    public ResourceStorageRuleViolationException() {
        super();
    }

    public ResourceStorageRuleViolationException(String message) {
        super(message);
    }

    public ResourceStorageRuleViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceStorageRuleViolationException(Throwable cause) {
        super(cause);
    }

}
