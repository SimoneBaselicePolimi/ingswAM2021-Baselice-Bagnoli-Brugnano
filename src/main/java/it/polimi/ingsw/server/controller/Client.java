package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.servermessage.ServerMessage;

import java.util.List;
import java.util.Optional;

/**
 * logic client
 */
public class Client {

    @SuppressWarnings("all")
    Optional<ClientHandler> handler = Optional.empty();

    public Client(String uniqueClientIdentifier) {
        this.uniqueClientIdentifier = uniqueClientIdentifier;
    }

    String uniqueClientIdentifier;

    String getClientId() {
        return uniqueClientIdentifier;
    }

    boolean hasHandler() {
        return handler.isPresent();
    }

    ClientHandler getHandler() {
        return handler.get();
    }

    void setHandler(ClientHandler handler) {
        this.handler = Optional.of(handler);
    }

}
