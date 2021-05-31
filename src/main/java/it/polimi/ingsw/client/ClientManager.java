package it.polimi.ingsw.client;

import it.polimi.ingsw.client.clientmessage.ClientMessage;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.ClientGameHistoryRepresentation;
import it.polimi.ingsw.client.servermessage.ServerMessage;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ClientManager {

    public static final int MESSAGE_QUEUE_SIZE = 1024;

    protected MessageSender serverSender;
    protected CompletableFuture<ServerMessage> serverAnswerable = null;
    protected Queue<ServerMessage> messagesToHandleFifo = new ArrayBlockingQueue<>(MESSAGE_QUEUE_SIZE);

    protected Player player;

    protected GameState gameState;

    protected Map<String, Object> deserializationContextMap = new ConcurrentHashMap<>();

    protected GameItemsManager gameItemsManager;
    protected ClientGameContextRepresentation gameContextRepresentation;
    protected ClientGameHistoryRepresentation gameHistoryRepresentation;

    public ClientManager(MessageSender serverSender) {
        this.serverSender = serverSender;
        gameState = GameState.PLAYER_REGISTRATION_AND_MATCHMAKING;
        Localization.getLocalizationInstance().setLocalizationLanguage("it");
        gameHistoryRepresentation = new ClientGameHistoryRepresentation(new ArrayList<>());
    }

    public synchronized void handleServerMessage(ServerMessage serverMessage) {
        messagesToHandleFifo.add(serverMessage);
        if(serverAnswerable != null) {
            serverAnswerable.complete(messagesToHandleFifo.poll());
        } else {
            //TODO
        }
    }

    public CompletableFuture<ServerMessage> sendMessageAndGetAnswer(ClientMessage messageToSend) {
        serverAnswerable = new CompletableFuture<>();
        serverSender.sendMessageToServer(messageToSend);
        return serverAnswerable;
    }

    public CompletableFuture<ServerMessage> getNewMessageFromServer() {
        serverAnswerable = new CompletableFuture<>();
        return serverAnswerable;
    }

    public MessageSender getServerSender() {
        return serverSender;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntryToDeserializationContextMap(String key, Object value) {
        deserializationContextMap.put(key, value);
    }

    public Map<String, Object> getDeserializationContextMap() {
        return deserializationContextMap;
    }

    public void removeEntryFromDeserializationContextMap(String key) {
        deserializationContextMap.remove(key);
    }

    public GameItemsManager getGameItemsManager() {
        return gameItemsManager;
    }

    public ClientGameContextRepresentation getGameContextRepresentation() {
        return gameContextRepresentation;
    }

    public ClientGameHistoryRepresentation getGameHistoryRepresentation() {
        return gameHistoryRepresentation;
    }

    public void setGameItemsManager(GameItemsManager gameItemsManager) {
        this.gameItemsManager = gameItemsManager;
    }

    public void setGameContextRepresentation(ClientGameContextRepresentation gameContextRepresentation) {
        this.gameContextRepresentation = gameContextRepresentation;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

}
