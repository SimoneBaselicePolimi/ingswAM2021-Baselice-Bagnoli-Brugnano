package it.polimi.ingsw.client.cli.clientstates;

import it.polimi.ingsw.client.ConsoleWriter;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.network.servermessage.ServerMessage;

public abstract class ClientState {

    protected ConsoleWriter consoleWriter;
    protected MessageSender serverSender;

    protected boolean isNewUserInputInvalid = false;
    protected boolean isStateDone = false;
    protected ClientState nextState = null;

    public ClientState(ConsoleWriter consoleWriter, MessageSender serverSender) {
        this.consoleWriter = consoleWriter;
        this.serverSender = serverSender;
    }

    public void handleUserInput(String input) {
        if(isNewUserInputInvalid)
            consoleWriter.writeToConsole("Wait, operation ongoing...\n");
        else
            _handleUserInput(input);
    }

    public boolean isStateDone() {
        return isStateDone;
    }

    public ClientState getNextState() {
        return nextState;
    }

    protected abstract void _handleUserInput(String input);
    public abstract void handleServerMessage(ServerMessage serverMessage);
    public abstract void onStateBegin();
    public abstract void onStateDone();
;


    protected void userCannotSendNewInput() {
        isNewUserInputInvalid = true;
    }

    protected void userCanSendNewInput() {
        isNewUserInputInvalid = false;
    }

    protected void nextState(ClientState newState) {
        isStateDone = true;
        nextState = newState;
    }

    protected void printLocalized(String placeholder, Object... args) {
        consoleWriter.writeToConsole(Localization.getLocalizationInstance().getString(placeholder, args));
    }

    protected void printLineLocalized(String placeholder, Object... args) {
        consoleWriter.writeNewLineToConsole(Localization.getLocalizationInstance().getString(placeholder, args));
    }
}
