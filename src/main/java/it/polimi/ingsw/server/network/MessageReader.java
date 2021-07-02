package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.controller.Client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class MessageReader {

    enum ReaderState {
        SETUP_FOR_NEXT_MESSAGE,
        READ_MESSAGE_HEADER,
        PARSE_MESSAGE_HEADER_AND_ALLOC_VAL_BUFF,
        READ_MESSAGE_VALUE,
        PARSE_MESSAGE_VALUE
    }

    protected Client client;
    protected SocketChannel socket;

    ReaderState readerState;

    public MessageReader(Client client, SocketChannel socket) {
        this.client = client;
        this.socket = socket;
        this.readerState = ReaderState.SETUP_FOR_NEXT_MESSAGE;
    }

    private ByteBuffer messageHeaderBuffer = ByteBuffer.allocate(
        RawMessage.TYPE_SIZE + RawMessage.FORMAT_SIZE + RawMessage.LENGTH_SIZE
    );

    private ByteBuffer unfinishedMessageValueBuffer = null;

    private byte unfinishedMessageType, unfinishedMessageFormat;
    private int unfinishedMessageLength;

    public List<ClientRawMessage> readIncomingMessages() throws IOException {

        List<ClientRawMessage> newCompleteMessages = new ArrayList<>();

        while(true) {

//            if(!socket.isConnected())
//                return new ArrayList<>();

            switch (readerState) {

                case SETUP_FOR_NEXT_MESSAGE -> {

                    messageHeaderBuffer.clear();
                    unfinishedMessageValueBuffer = null;

                    readerState = ReaderState.READ_MESSAGE_HEADER;
                }

                case READ_MESSAGE_HEADER -> {

                    socket.read(messageHeaderBuffer);

                    if (messageHeaderBuffer.hasRemaining())
                        return newCompleteMessages;
                    else
                        readerState = ReaderState.PARSE_MESSAGE_HEADER_AND_ALLOC_VAL_BUFF;
                }

                case PARSE_MESSAGE_HEADER_AND_ALLOC_VAL_BUFF -> {

                    messageHeaderBuffer.flip();
                    unfinishedMessageType = messageHeaderBuffer.get(0);
                    unfinishedMessageFormat = messageHeaderBuffer.get(RawMessage.TYPE_SIZE);
                    unfinishedMessageLength = messageHeaderBuffer.getInt(RawMessage.TYPE_SIZE + RawMessage.FORMAT_SIZE);
                    unfinishedMessageValueBuffer = ByteBuffer.allocate(unfinishedMessageLength);

                    readerState = ReaderState.READ_MESSAGE_VALUE;
                }

                case READ_MESSAGE_VALUE -> {

                    socket.read(unfinishedMessageValueBuffer);

                    if (unfinishedMessageValueBuffer.hasRemaining())
                        return newCompleteMessages;
                    else
                        readerState = ReaderState.PARSE_MESSAGE_VALUE;
                }

                case PARSE_MESSAGE_VALUE -> {

                    unfinishedMessageValueBuffer.flip();
                    byte[] messageValue = unfinishedMessageValueBuffer.array();
                    newCompleteMessages.add(new ClientRawMessage(
                        client,
                        unfinishedMessageType,
                        unfinishedMessageFormat,
                        unfinishedMessageLength,
                        messageValue
                    ));

                    readerState = ReaderState.SETUP_FOR_NEXT_MESSAGE;
                }

            }
        }
    }

}
