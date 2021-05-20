package it.polimi.ingsw.client;

import it.polimi.ingsw.server.controller.clientmessage.ClientMessage;

public interface ConsoleWriter {

    void writeToConsole(String text);
    void writeNewLineToConsole(String line);

}
