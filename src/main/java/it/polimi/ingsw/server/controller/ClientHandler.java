package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.controller.clientmessage.ClientMessage;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Definition of a generic client handler for one or more clients.
 * The handler will manage all the I/O connection for the clients it manages.
 *
 * Every handler will run on a specific thread
 */
public abstract class ClientHandler extends Thread {

    static final int MESSAGES_TO_HANDLE_QUEUE_SIZE = 1024;

    Set<Client> managedClients = new HashSet<>();

    ArrayBlockingQueue<ClientMessage> messagesToHandle = new ArrayBlockingQueue<>(MESSAGES_TO_HANDLE_QUEUE_SIZE);

    ServerMessageSender messageSender;

    ClientHandler(Set<Client> clientsToManage, ServerMessageSender messageSender) {
        clientsToManage.forEach(this::registerClientWithThisHandler);
        this.messageSender = messageSender;
    }

    protected void registerClientWithThisHandler(Client client) {
        ClientHandler oldHandler = client.hasHandler() ? client.getHandler() : null;
        managedClients.add(client);
        client.setHandler(this);
        if(oldHandler != null)
            //TODO in the very rare case that a message was sent to the client while switching handler an error may occur
            client.getHandler().deregisterClientWithThisHandler(client);
    }

    public void deregisterClientWithThisHandler(Client client) {
        managedClients.remove(client);
    }

    /**
     * Add message to the queue of messages to handle.
     * @param clientMessage message to add to the queue
     * @throws IllegalArgumentException if the handler does not manage the client that sent this message.
     */
    public void addNewMessageToQueue(ClientMessage clientMessage) throws IllegalArgumentException {

        if(!managedClients.contains(clientMessage.client))
            throw new IllegalArgumentException("Internal error! Unknown client: the message sender client is not in the pool " +
                "of clients managed by this handler"
            );

        messagesToHandle.add(clientMessage);

    }

    protected void handleNewMessage(ClientMessage message) {};

    protected void sendMessage(ServerMessage message, Client receiver) {
        messageSender.sendMessage(message, receiver);
    }

    @Override
    public void run() {
        while (true) {
            try {
                ClientMessage newMessage =  messagesToHandle.take();
                handleNewMessage(newMessage);
            } catch (InterruptedException e) {
                e.printStackTrace();
                //TODO
            }
        }
    }

}
