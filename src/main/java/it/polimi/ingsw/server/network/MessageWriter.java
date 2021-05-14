package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.controller.Client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class MessageWriter {

    static final int MESSAGES_TO_SEND_QUEUE_SIZE = 1024;


    protected Client client;
    protected SocketChannel socket;
    protected Selector writeSelector;

    Queue<ServerRawMessage> messagesToSendQueue;

    ServerRawMessage unfinishedMessage = null;

    private ByteBuffer messageHeaderBuffer = ByteBuffer.allocate(
        RawMessage.TYPE_SIZE + RawMessage.FORMAT_SIZE + RawMessage.LENGTH_SIZE
    );

    private ByteBuffer unfinishedMessageValueBuffer = null;


    public MessageWriter(Client client, SocketChannel socket, Selector writeSelector) {
        this.client = client;
        this.socket = socket;
        this.writeSelector = writeSelector;
        this.messagesToSendQueue = new ArrayBlockingQueue<>(MESSAGES_TO_SEND_QUEUE_SIZE);
    }

    void addServerMessageToOutboundQueue(ServerRawMessage serverMessage) throws ClosedChannelException {

        //Register socket with writeSelector if it has not been registered yet
        if(messagesToSendQueue.isEmpty()) {
            SelectionKey key = socket.register(writeSelector, SelectionKey.OP_WRITE);
            key.attach(client);
        }

        messagesToSendQueue.add(serverMessage);
    }

    void flushMessagesInQueue() throws IOException {

        if(unfinishedMessage == null) {
            unfinishedMessage = messagesToSendQueue.poll();

            //Write message content in buffers
            if(unfinishedMessage != null) {

                messageHeaderBuffer.put(unfinishedMessage.type);
                messageHeaderBuffer.put(unfinishedMessage.valueFormat);
                messageHeaderBuffer.putInt(unfinishedMessage.valueLength);
                messageHeaderBuffer.flip();

                unfinishedMessageValueBuffer = ByteBuffer.allocate(unfinishedMessage.valueLength);
                unfinishedMessageValueBuffer.put(unfinishedMessage.value);
                unfinishedMessageValueBuffer.flip();

            }

        }

        //Write buffers content to socket
        if(unfinishedMessage != null ) {

            if(messageHeaderBuffer.hasRemaining()) {
                socket.write(messageHeaderBuffer);
            }

            if(!messageHeaderBuffer.hasRemaining() && unfinishedMessageValueBuffer.hasRemaining()) {
                socket.write(unfinishedMessageValueBuffer);

                if(!unfinishedMessageValueBuffer.hasRemaining()) {
                    messageHeaderBuffer.clear();
                    unfinishedMessageValueBuffer = null;
                    unfinishedMessage = null;

                    //If we do not have anything else to write we can deregister the socket from the writeSelector
                    if(messagesToSendQueue.isEmpty()) {
                        socket.keyFor(writeSelector).cancel();
                    }

                }
            }

        }

    }

}
