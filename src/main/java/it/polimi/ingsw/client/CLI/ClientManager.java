package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.CLI.clientstates.AskAndRegisterPlayerNameClientState;
import it.polimi.ingsw.client.CLI.clientstates.ClientState;
import it.polimi.ingsw.client.ConsoleWriter;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.network.servermessage.ServerMessage;

public class ClientManager {

    protected ConsoleWriter consoleWriter;
    protected MessageSender serverSender;

    ClientState currentState;

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
        return new AskAndRegisterPlayerNameClientState(consoleWriter, serverSender);
    }

    protected void changeStateIfDone() {
        if(currentState.isStateDone()) {
            currentState.onStateDone();
            currentState = currentState.getNextState();
            currentState.onStateBegin();
        }
    }

}
