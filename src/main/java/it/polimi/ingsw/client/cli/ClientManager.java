package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.cli.clientstates.AskAndRegisterPlayerNameClientState;
import it.polimi.ingsw.client.cli.clientstates.ClientState;
import it.polimi.ingsw.client.ConsoleWriter;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.client.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
public class ClientManager {

    protected ConsoleWriter consoleWriter;
    protected MessageSender serverSender;

    protected ClientState currentState;
    protected Player player;

    protected Map<String, Object> deserializationContextMap = new ConcurrentHashMap<>();

    protected GameItemsManager gameItemsManager;
    protected ClientGameContextRepresentation gameContextRepresentation;

    public ClientManager(ConsoleWriter consoleWriter, MessageSender serverSender) {
        this.consoleWriter = consoleWriter;
        this.serverSender = serverSender;
        this.currentState = getInitialState();
        Localization.getLocalizationInstance().setLocalizationLanguage("it");
        currentState.onStateBegin();
    }

    public synchronized void handleServerMessage(ServerMessage serverMessage) {
        currentState.handleServerMessage(serverMessage);
        changeStateIfDone();
    }

    public synchronized void handleUserInput(String input) {
        currentState.handleUserInput(input);
        changeStateIfDone();
    }

    protected ClientState getInitialState() {
        return new AskAndRegisterPlayerNameClientState(this);
    }

    protected void changeStateIfDone() {
        if(currentState.isStateDone()) {
            currentState.onStateDone();
            currentState = currentState.getNextState();
            currentState.onStateBegin();
        }
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

    public void removeEntryFromDeserializationContextMap(String key) {
        deserializationContextMap.remove(key);
    }

    public GameItemsManager getGameItemsManager() {
        return gameItemsManager;
    }

    public ClientGameContextRepresentation getGameContextRepresentation() {
        return gameContextRepresentation;
    }

    public void setGameComponents(
        GameItemsManager gameItemsManager,
        ClientGameContextRepresentation gameContextRepresentation
    ) {
        this.gameItemsManager = gameItemsManager;
        this.gameContextRepresentation = gameContextRepresentation;
    }

}
