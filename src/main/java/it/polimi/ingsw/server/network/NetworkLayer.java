package it.polimi.ingsw.server.network;

import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.server.controller.Client;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class NetworkLayer implements ServerRawMessageSender {

    static final int NEW_CONNECTIONS_QUEUE_SIZE = 1024;

    private int tcpPort;

    private ClientRawMessageProcessor inboundMessagesProcessor;

    private Map<Client, SocketConnection> clientConnections;

    SocketConnectionsAccepter newConnectionsAccepter;
    SocketConnectionsProcessor netMessagesProcessor;

    public NetworkLayer(int tcpPort, ClientRawMessageProcessor inboundMessagesProcessor) {
        this.tcpPort = tcpPort;
        this.inboundMessagesProcessor = inboundMessagesProcessor;
        clientConnections = new ConcurrentHashMap<>();
    }

    public void start() throws IOException {

        Queue<SocketChannel> newConnectionsQueue = new ArrayBlockingQueue<>(NEW_CONNECTIONS_QUEUE_SIZE);

        newConnectionsAccepter = new SocketConnectionsAccepter(tcpPort, newConnectionsQueue);
        netMessagesProcessor = new SocketConnectionsProcessor(
            this,
            newConnectionsQueue,
            inboundMessagesProcessor,
            clientConnections
        );
        new Thread(newConnectionsAccepter).start();
        new Thread(netMessagesProcessor).start();


    }

    public void sendMessage(ServerRawMessage serverMessage) {
        SocketConnection connection = clientConnections.get(serverMessage.receiver);
        try {
            connection.sendMessage(serverMessage);
        } catch (ClosedChannelException e) {
            //TODO
            e.printStackTrace();
        }
    }

    public void removeConnection(Client client) {
        try {
            clientConnections.get(client).getSocketChannel().keyFor(netMessagesProcessor.readSelector).interestOps(0);
            clientConnections.get(client).getSocketChannel().keyFor(netMessagesProcessor.writeSelector).interestOps(0);
            clientConnections.get(client).getSocketChannel().close();
        } catch (IOException e) {
            ProjectLogger.getLogger().log(e);
        }
    }

}
