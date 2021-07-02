package it.polimi.ingsw.client;

import it.polimi.ingsw.client.clientmessage.ClientMessage;
import it.polimi.ingsw.client.gameupdate.ClientGameUpdate;
import it.polimi.ingsw.client.gui.GuiClientManager;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.ClientGameHistoryRepresentation;
import it.polimi.ingsw.client.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.client.servermessage.ServerMessage;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ClientManager {

    protected static ClientManager instance = null;

    public static ClientManager initializeInstance(MessageSender serverSender) {
        instance = new ClientManager(serverSender);
        return instance;
    }

    public static ClientManager getInstance() throws NullPointerException {
        if (instance != null)
            return instance;
        else
            throw new NullPointerException();
    }

    protected MessageSender serverSender;
    protected CompletableFuture<ServerMessage> serverAnswerable = null;

    protected Player myPlayer;

    protected GameState gameState;

    protected Map<String, Object> contextInfoMap = new ConcurrentHashMap<>();

    protected GameItemsManager gameItemsManager;
    protected ClientGameContextRepresentation gameContextRepresentation;
    protected ClientGameHistoryRepresentation gameHistoryRepresentation;

    public ClientManager(MessageSender serverSender) {
        this.serverSender = serverSender;
        gameState = GameState.PLAYER_REGISTRATION_AND_MATCHMAKING;
        Localization.getLocalizationInstance().setLocalizationLanguage("it");
        gameHistoryRepresentation = new ClientGameHistoryRepresentation(new ArrayList<>());
    }

    public void onConnectionWithServerDropped() {}

    public synchronized void handleServerMessage(ServerMessage serverMessage) {
        if(serverAnswerable != null && !serverAnswerable.isDone()) {
            serverAnswerable.complete(serverMessage);
        } else {
            ServerMessageUtils.ifMessageTypeCompute(
                serverMessage,
                GameUpdateServerMessage.class,
                message -> {
                    handleGameUpdates(message.gameUpdates);
                    return null;
                }
            ).apply();
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

    public void handleGameUpdates(Set<ClientGameUpdate> gameUpdates) {
        gameUpdates.forEach(update -> update.getHandler().handleGameUpdate(update, this));
    }

    public MessageSender getServerSender() {
        return serverSender;
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(Player myPlayer) {
        this.myPlayer = myPlayer;
    }

    public void addEntryToContextInfoMap(String key, Object value) {
        contextInfoMap.put(key, value);
    }

    public Object getEntryInContextInfoMap(String key) {
        return contextInfoMap.get(key);
    }

    public Map<String, Object> getContextInfoMap() {
        return contextInfoMap;
    }

    public void removeEntryFromContextInfoMap(String key) {
        contextInfoMap.remove(key);
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
