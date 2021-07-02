package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;

import java.util.concurrent.CompletableFuture;

public class CliClientManager extends ClientManager {

    protected static final int DEFAULT_CONSOLE_DISPLAY_WIDTH = 196;
    protected static final int DEFAULT_CONSOLE_DISPLAY_HEIGHT = 40;

    protected ConsoleWriter consoleWriter;

    protected CompletableFuture<String> userAnswerable = null;

    protected UserIOLogger userIOLogger;

    protected int consoleDisplayWidth = DEFAULT_CONSOLE_DISPLAY_WIDTH;
    protected int consoleDisplayHeight = DEFAULT_CONSOLE_DISPLAY_HEIGHT;


    public static CliClientManager initializeInstance(ConsoleWriter consoleWriter, MessageSender serverSender) {
        instance = new CliClientManager(consoleWriter, serverSender);
        return (CliClientManager) instance;
    }

    public static CliClientManager getInstance() throws NullPointerException {
        if (instance != null)
            return (CliClientManager) instance;
        else
            throw new NullPointerException();
    }

    public CliClientManager(ConsoleWriter consoleWriter, MessageSender serverSender) {
        super(serverSender);
        this.consoleWriter = consoleWriter;
    }

    public synchronized void handleUserInput(String input) {
        userIOLogger.logMessageFromUser(input);
        if(userAnswerable != null)
            userAnswerable.complete(input);
        else
            tellUserLocalized("client.errors.userInputNotExpected");
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
        userIOLogger.logMessageForUser(text);
    }

    public void tellUserLocalized(String requestPlaceholder, Object... args) {
        tellUser(Localization.getLocalizationInstance().getString(requestPlaceholder, args));
    }

    public ConsoleWriter getConsoleWriter() {
        return consoleWriter;
    }

    public UserIOLogger getConsoleIOLogger() {
        return userIOLogger;
    }

    public void setNewUserIOLogger(UserIOLogger userIOLogger) {
        this.userIOLogger = userIOLogger;
    }

    public int getConsoleDisplayWidth() {
        return consoleDisplayWidth;
    }

    public int getConsoleDisplayHeight() {
        return consoleDisplayHeight;
    }

    @Override
    public void onConnectionWithServerDropped() {
        tellUserLocalized("client.errors.onConnectionWithServerDropped");
        System.exit(0);
    }

    @Override
    public void onAnotherPlayerDisconnected(Player player) {
        tellUserLocalized("client.errors.onAnotherPlayerDisconnected", player.playerName);
        System.exit(0);
    }

}
