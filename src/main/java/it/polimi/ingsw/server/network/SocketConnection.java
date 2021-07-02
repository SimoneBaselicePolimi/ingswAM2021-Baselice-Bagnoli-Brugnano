package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.controller.Client;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.List;

public class SocketConnection {

    protected Client client;

    protected SocketChannel socket;
    protected MessageReader reader;
    protected MessageWriter writer;
    protected Selector writeSelector;

    public SocketConnection(
        Client logicLevelClient,
        SocketChannel socket,
        Selector writeSelector
    ) {
        this.client = logicLevelClient;
        this.socket = socket;
        this.reader = new MessageReader(client, socket);
        this.writer = new MessageWriter(client, socket, writeSelector);
        this.writeSelector = writeSelector;
    }

    public Client getClient() {
        return client;
    }

    public List<ClientRawMessage> readIncomingMessages() throws IOException {
        return reader.readIncomingMessages();
    }

    public void sendMessage(ServerRawMessage serverMessage) throws ClosedChannelException {
        writer.addServerMessageToOutboundQueue(serverMessage);
    }

    public void flushOutboundMessages() throws IOException {
        writer.flushMessagesInQueue();
    }

    public SocketChannel getSocketChannel() {
        return socket;
    }

}
