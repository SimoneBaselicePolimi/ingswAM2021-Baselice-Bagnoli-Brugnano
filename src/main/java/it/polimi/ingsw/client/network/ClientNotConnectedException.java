package it.polimi.ingsw.client.network;

import java.security.PrivilegedActionException;

public class ClientNotConnectedException extends Throwable {

    public ClientNotConnectedException() {
    }

    public ClientNotConnectedException(String message) {
        super(message);
    }

    public ClientNotConnectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientNotConnectedException(Throwable cause) {
        super(cause);
    }

}
