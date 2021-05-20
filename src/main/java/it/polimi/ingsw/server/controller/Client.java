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

    public synchronized String getClientId() {
        return uniqueClientIdentifier;
    }

    public synchronized boolean hasHandler() {
        return handler.isPresent();
    }

    public synchronized ClientHandler getHandler() {
        return handler.get();
    }

    synchronized void setHandler(ClientHandler handler) {
        this.handler = Optional.of(handler);
    }

}
