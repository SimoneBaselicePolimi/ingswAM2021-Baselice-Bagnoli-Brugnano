package it.polimi.ingsw.server.network;

import it.polimi.ingsw.network.servermessage.ServerMessage;

public interface ServerRawMessageSender {

    void sendMessage(ServerRawMessage message);

}
