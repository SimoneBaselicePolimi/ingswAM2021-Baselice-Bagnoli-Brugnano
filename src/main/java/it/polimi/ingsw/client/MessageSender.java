package it.polimi.ingsw.client;


import it.polimi.ingsw.client.clientmessage.ClientMessage;

public interface MessageSender {

    void sendMessageToServer(ClientMessage message);

}
