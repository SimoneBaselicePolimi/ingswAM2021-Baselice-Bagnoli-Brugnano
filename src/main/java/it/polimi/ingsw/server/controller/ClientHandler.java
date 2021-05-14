package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.controller.Client;
import it.polimi.ingsw.server.controller.ClientMessage;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;

/**
 * Definition of a generic client handler for one or more clients.
 * The handler will manage all the I/O connection for the clients it manages.
 *
 * Every handler will run on a specific thread
 */
public abstract class ClientHandler implements Runnable {

    static final int MESSAGES_TO_HANDLE_QUEUE_SIZE = 1024;
    //static final int OUTBOUND_MESSAGES_QUEUE_SIZE = 1024;

    Set<Client> managedClients = new HashSet<>();

    Queue<ClientMessage> messagesToHandle = new ArrayBlockingQueue<>(MESSAGES_TO_HANDLE_QUEUE_SIZE);

    //Queue<ServerMessage> outboundMessagesQueue = new ArrayBlockingQueue<>(OUTBOUND_MESSAGES_QUEUE_SIZE);

    ServerMessageSender messageSender;

    ClientHandler(Set<Client> clientsToManage, ServerMessageSender messageSender) {
        clientsToManage.forEach(this::registerClientWithThisHandler);
        this.messageSender = messageSender;
    }

    protected void registerClientWithThisHandler(Client client) {
        client.setHandler(this);
        managedClients.add(client);
    }

    /**
     * Add message to the queue of messages to handle.
     * @param clientMessage message to add to the queue
     * @throws IllegalArgumentException if the handler does not manage the client that sent this message.
     */
    public void addClientMessageToQueue(ClientMessage clientMessage) throws IllegalArgumentException {

        if(!managedClients.contains(clientMessage.client))
            throw new IllegalArgumentException("Unknown client error! The message sender client is not in the pool " +
                "of clients managed by this handler"
            );

        messagesToHandle.add(clientMessage);

    }

//    public Queue<ServerMessage> getOutboundMessagesQueue() {
//        return outboundMessagesQueue;
//    }

}
