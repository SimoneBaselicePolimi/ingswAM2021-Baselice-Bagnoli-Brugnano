package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.cli.clientstates.AskAndRegisterPlayerNameClientState;
import it.polimi.ingsw.client.cli.clientstates.ClientState;
import it.polimi.ingsw.client.ConsoleWriter;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;

public class ClientManager {

    protected ConsoleWriter consoleWriter;
    protected MessageSender serverSender;

    ClientState currentState;
    Player player;

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
}
