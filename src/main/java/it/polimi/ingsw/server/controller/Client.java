package it.polimi.ingsw.server.controller;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * logic client
 */
public class Client {

    @SuppressWarnings("all")
    Optional<ClientHandler> handler = Optional.empty();

    Map<String, Object> deserializationContextMap = new ConcurrentHashMap<>(Map.of("client", this));

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

    public Map<String, Object> getDeserializationContextMap() {
        return deserializationContextMap;
    }

    public void addEntryToDeserializationContextMap(String key, Object value) {
        deserializationContextMap.put(key, value);
    }

    public void removeEntryFromDeserializationContextMap(String key) {
        deserializationContextMap.remove(key);
    }

}
