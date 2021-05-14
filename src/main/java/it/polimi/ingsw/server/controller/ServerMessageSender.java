package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.servermessage.ServerMessage;

public interface ServerMessageSender {

    void sendMessage(ServerMessage message);

}
