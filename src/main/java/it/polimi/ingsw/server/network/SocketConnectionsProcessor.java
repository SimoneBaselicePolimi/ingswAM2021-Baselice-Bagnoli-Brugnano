package it.polimi.ingsw.server.network;

import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.server.controller.Client;

import java.io.IOException;
import java.nio.channels.*;
import java.util.*;

public class SocketConnectionsProcessor implements Runnable {

    NetworkLayer networkLayer;

    ClientRawMessageProcessor inboundMessagesProcessor;

    private Queue<SocketChannel> newSocketsQueue;
    final private Map<Client, SocketConnection> clientConnections;
    final private Map<SelectableChannel, SocketConnection> channelToConnectionFallbackMap = new HashMap<>();

    private Selector readSelector;
    private Selector writeSelector;

    public SocketConnectionsProcessor(
        NetworkLayer networkLayer,
        Queue<SocketChannel> newSocketsQueue,
        ClientRawMessageProcessor inboundMessagesProcessor,
        Map<Client, SocketConnection> clientConnections
    ) throws IOException {
        this.networkLayer = networkLayer;
        this.newSocketsQueue = newSocketsQueue;
        this.inboundMessagesProcessor = inboundMessagesProcessor;
        this.clientConnections = clientConnections;
        readSelector = Selector.open();
        writeSelector = Selector.open();
    }

    @Override
    public void run() {

        while(true) {
            try {
                acceptNewConnections();
                readFromSockets();
                writeToSockets();
            } catch (IOException e) {
                //TODO
                e.printStackTrace();
            }

        }

    }

    public void acceptNewConnections() throws IOException {

        SocketChannel newSocket = this.newSocketsQueue.poll();

        while(newSocket != null){
            newSocket.configureBlocking(false);

            SocketConnection newConnection = new SocketConnection(
                new Client(String.format("%s", newSocket.socket().getRemoteSocketAddress())),
                newSocket,
                writeSelector
            );

            clientConnections.put(newConnection.getClient(), newConnection);

            try {
                newSocket.register(this.readSelector, SelectionKey.OP_READ, newConnection);
                channelToConnectionFallbackMap.put(newSocket, newConnection);

                inboundMessagesProcessor.processNewClientConnection(newConnection.getClient(), networkLayer);
                newSocket = this.newSocketsQueue.poll();

            } catch (Exception c) {
                ProjectLogger.getLogger().log(c);
                throw c;
            }

        }

    }

    public void readFromSockets() throws IOException {

        int readReady = this.readSelector.selectNow();

        if(readReady > 0){

            Set<SelectionKey> selectedKeys = this.readSelector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while(keyIterator.hasNext()) {

                SelectionKey key = keyIterator.next();

                SocketConnection connection;
                if(key.attachment() != null)
                    connection = (SocketConnection) key.attachment();
                else
                    connection = channelToConnectionFallbackMap.get(key.channel());

                connection.readIncomingMessages().forEach(
                    m -> inboundMessagesProcessor.processNewMessage(m, networkLayer)
                );

                keyIterator.remove();

            }

            selectedKeys.clear();

        }

    }

    public void writeToSockets() throws IOException {

        int writeReady = this.writeSelector.selectNow();

        if(writeReady > 0){
            Set<SelectionKey> selectionKeys = this.writeSelector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while(keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();

                SocketConnection connection;
                if(key.attachment() != null) {
                    Client client = (Client) key.attachment();
                    connection = clientConnections.get(client);
                } else {
                    connection = channelToConnectionFallbackMap.get(key.channel());
                }

                connection.flushOutboundMessages();

                keyIterator.remove();
            }

            selectionKeys.clear();

        }
    }

}
