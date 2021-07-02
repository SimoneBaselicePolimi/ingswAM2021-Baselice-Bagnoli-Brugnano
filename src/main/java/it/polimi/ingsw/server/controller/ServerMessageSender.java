package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.servermessage.ServerMessage;

public interface ServerMessageSender {

    void sendMessage(ServerMessage message, Client receiver);

}
