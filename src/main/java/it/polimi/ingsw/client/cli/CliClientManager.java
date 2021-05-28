package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.client.cli.view.PreGameView;
import it.polimi.ingsw.client.clientmessage.ClientMessage;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.ClientGameHistoryRepresentation;
import it.polimi.ingsw.client.servermessage.ServerMessage;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class CliClientManager extends ClientManager {


    protected static final int DEFAULT_CONSOLE_DISPLAY_WIDTH = 100;
    protected static final int DEFAULT_CONSOLE_DISPLAY_HEIGHT = 40;

    protected ConsoleWriter consoleWriter;

    protected CompletableFuture<String> userAnswerable = null;

    protected UserIOLogger userIOLogger;

    protected int consoleDisplayWidth = DEFAULT_CONSOLE_DISPLAY_WIDTH;
    protected int consoleDisplayHeight = DEFAULT_CONSOLE_DISPLAY_HEIGHT;


    public CliClientManager(ConsoleWriter consoleWriter, MessageSender serverSender) {
        super(serverSender);
        this.consoleWriter = consoleWriter;
    }

    public synchronized void handleUserInput(String input) {

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

}
