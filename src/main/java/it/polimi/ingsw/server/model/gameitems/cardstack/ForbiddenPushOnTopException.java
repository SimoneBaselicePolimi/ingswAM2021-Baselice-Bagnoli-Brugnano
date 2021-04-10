package it.polimi.ingsw.server.model.gameitems.cardstack;

public class ForbiddenPushOnTopException extends Exception {
    public ForbiddenPushOnTopException() {
    }

    public ForbiddenPushOnTopException(String message) {
        super(message);
    }

    public ForbiddenPushOnTopException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenPushOnTopException(Throwable cause) {
        super(cause);
    }

}
