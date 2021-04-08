package it.polimi.ingsw.server.model.storage;

import java.security.PrivilegedActionException;
/**
 * This exception will be thrown when you try to add some resources to a storage
 * by violating a specific rule that the storage implements.
 */
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
