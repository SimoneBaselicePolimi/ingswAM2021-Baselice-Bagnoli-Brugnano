package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.ConsoleWriter;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.client.clientmessage.ClientMessage;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class NewClientManager {

    public static final int MESSAGE_QUEUE_SIZE = 1024;

    protected ConsoleWriter consoleWriter;
    protected MessageSender serverSender;

    protected Player player;

    protected Map<String, Object> deserializationContextMap = new ConcurrentHashMap<>();

    protected GameItemsManager gameItemsManager;
    protected ClientGameContextRepresentation gameContextRepresentation;

    protected CompletableFuture<ServerMessage> serverAnswerable = null;
    protected CompletableFuture<String> userAnswerable = null;

protected Queue<ServerMessage> messagesToHandleFifo = new ArrayBlockingQueue<ServerMessage>(MESSAGE_QUEUE_SIZE);

    public NewClientManager(ConsoleWriter consoleWriter, MessageSender serverSender) {
        this.consoleWriter = consoleWriter;
        this.serverSender = serverSender;
        Localization.getLocalizationInstance().setLocalizationLanguage("it");
    }

    public synchronized void handleServerMessage(ServerMessage serverMessage) {
        messagesToHandleFifo.add(serverMessage);
        if(serverAnswerable != null) {
            serverAnswerable.complete(messagesToHandleFifo.poll());
        } else {
            //TODO
        }
    }

    public synchronized void handleUserInput(String input) {
        if(userAnswerable != null)
            userAnswerable.complete(input);
        else
            tellUserLocalized("client.errors.userInputNotExpected");
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

    public CompletableFuture<String> askUser(String request) {
        userAnswerable = new CompletableFuture<>();
        tellUser(request);
        return userAnswerable;
    }

    public CompletableFuture<String> askUserLocalized(String requestPlaceholder, Object... args) {
        return askUser(Localization.getLocalizationInstance().getString(requestPlaceholder, args));
    }

    public void tellUser(String text) {
        consoleWriter.writeNewLineToConsole(text);
    }

    public void tellUserLocalized(String requestPlaceholder, Object... args) {
        tellUser(Localization.getLocalizationInstance().getString(requestPlaceholder, args));
    }

    public ConsoleWriter getConsoleWriter() {
        return consoleWriter;
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

    public void setGameItemsManager(GameItemsManager gameItemsManager) {
        this.gameItemsManager = gameItemsManager;
    }

    public void setGameContextRepresentation(ClientGameContextRepresentation gameContextRepresentation) {
        this.gameContextRepresentation = gameContextRepresentation;
    }

}
