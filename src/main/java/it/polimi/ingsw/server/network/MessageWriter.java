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

    enum WriterState {
        SETUP_FOR_NEXT_MESSAGE,
        WAIT_FOR_NEXT_MESSAGE,
        PUT_MESSAGE_CONTENT_IN_BUFFERS,
        WRITE_MESSAGE_HEADER,
        WRITE_MESSAGE_VALUE,
        DEREGISTER_OR_CONTINUE
    }

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

    WriterState writerState;

    public MessageWriter(Client client, SocketChannel socket, Selector writeSelector) {
        this.client = client;
        this.socket = socket;
        this.writeSelector = writeSelector;
        this.messagesToSendQueue = new ArrayBlockingQueue<>(MESSAGES_TO_SEND_QUEUE_SIZE);
        writerState = WriterState.SETUP_FOR_NEXT_MESSAGE;
    }

    void addServerMessageToOutboundQueue(ServerRawMessage serverMessage) throws ClosedChannelException {

        //Register socket with writeSelector if it has not been registered yet
        if(messagesToSendQueue.isEmpty()) {
            if(socket.keyFor(writeSelector) == null) {
                socket.register(writeSelector, SelectionKey.OP_WRITE, client);
            } else {
                socket.keyFor(writeSelector).interestOps(SelectionKey.OP_WRITE);
            }
        }

        messagesToSendQueue.add(serverMessage);
    }

    void flushMessagesInQueue() throws IOException {

        while(true) {

            switch (writerState) {
                case SETUP_FOR_NEXT_MESSAGE -> {
                    messageHeaderBuffer.clear();
                    unfinishedMessageValueBuffer = null;
                    unfinishedMessage = null;

                    writerState = WriterState.WAIT_FOR_NEXT_MESSAGE;
                }
                case WAIT_FOR_NEXT_MESSAGE -> {
                    unfinishedMessage = messagesToSendQueue.poll();

                    if(unfinishedMessage == null)
                        return;
                    else
                        writerState = WriterState.PUT_MESSAGE_CONTENT_IN_BUFFERS;
                }
                case PUT_MESSAGE_CONTENT_IN_BUFFERS -> {
                    messageHeaderBuffer.put(unfinishedMessage.type);
                    messageHeaderBuffer.put(unfinishedMessage.valueFormat);
                    messageHeaderBuffer.putInt(unfinishedMessage.valueLength);
                    messageHeaderBuffer.flip();

                    unfinishedMessageValueBuffer = ByteBuffer.allocate(unfinishedMessage.valueLength);
                    unfinishedMessageValueBuffer.put(unfinishedMessage.value);
                    unfinishedMessageValueBuffer.flip();

                    writerState = WriterState.WRITE_MESSAGE_HEADER;
                }
                case WRITE_MESSAGE_HEADER -> {
                    socket.write(messageHeaderBuffer);

                    if (messageHeaderBuffer.hasRemaining())
                        return;
                    else
                        writerState = WriterState.WRITE_MESSAGE_VALUE;
                }
                case WRITE_MESSAGE_VALUE -> {
                    socket.write(unfinishedMessageValueBuffer);

                    if (unfinishedMessageValueBuffer.hasRemaining())
                        return;
                    else
                        writerState = WriterState.DEREGISTER_OR_CONTINUE;
                }
                case DEREGISTER_OR_CONTINUE -> {
                    if(messagesToSendQueue.isEmpty()) {
                        socket.keyFor(writeSelector).interestOps(0);
                        return;
                    } else {
                        writerState = WriterState.SETUP_FOR_NEXT_MESSAGE;
                    }
                }
            }

        }

    }

}
