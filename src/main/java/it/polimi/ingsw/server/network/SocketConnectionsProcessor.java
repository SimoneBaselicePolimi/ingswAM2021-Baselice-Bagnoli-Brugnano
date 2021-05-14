package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.controller.Client;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class SocketConnectionsProcessor implements Runnable {

    ClientRawMessageProcessor inboundMessagesProcessor;

    private Queue<SocketChannel> newSocketsQueue;
    final private Map<Client, SocketConnection> clientConnections;

    private Selector readSelector;
    private Selector writeSelector;

    public SocketConnectionsProcessor(
        Queue<SocketChannel> newSocketsQueue,
        ClientRawMessageProcessor inboundMessagesProcessor,
        Map<Client, SocketConnection> clientConnections
    ) throws IOException {
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
                new Client(newSocket.socket().getInetAddress().getHostAddress()),
                newSocket,
                writeSelector
            );

            clientConnections.put(newConnection.getClient(), newConnection);

            SelectionKey key = newSocket.register(this.readSelector, SelectionKey.OP_READ);
            key.attach(newConnection);

            inboundMessagesProcessor.processNewClientConnection(newConnection.getClient());

            newSocket = this.newSocketsQueue.poll();
        }

    }

    public void readFromSockets() throws IOException {

        int readReady = this.readSelector.selectNow();

        if(readReady > 0){

            Set<SelectionKey> selectedKeys = this.readSelector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while(keyIterator.hasNext()) {

                SelectionKey key = keyIterator.next();
                SocketConnection connection = (SocketConnection) key.attachment();

                connection.readIncomingMessages().forEach(inboundMessagesProcessor::processNewMessage);

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

                SocketConnection connection = (SocketConnection) key.attachment();

                connection.flushOutboundMessages();

                keyIterator.remove();
            }

            selectionKeys.clear();

        }
    }


}
