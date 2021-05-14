package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.controller.Client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class MessageReader {

    protected Client client;
    protected SocketChannel socket;

    public MessageReader(Client client, SocketChannel socket) {
        this.client = client;
        this.socket = socket;
    }

    private ByteBuffer messageHeaderBuffer = ByteBuffer.allocate(
        RawMessage.TYPE_SIZE + RawMessage.FORMAT_SIZE + RawMessage.LENGTH_SIZE
    );

    private ByteBuffer unfinishedMessageValueBuffer = null;

    private byte unfinishedMessageType, unfinishedMessageFormat;
    private int unfinishedMessageLength;

    public List<ClientRawMessage> readIncomingMessages() throws IOException {

        List<ClientRawMessage> newCompleteMessages = new ArrayList<>(1);

        //check if we still need to read some of the bytes of the message header
        if(messageHeaderBuffer.hasRemaining()) {
            socket.read(messageHeaderBuffer);

            //if the header has now been completely read we can parse it, reset the buffer and allocate a buffer
            // of the right size for the message body
            if(!messageHeaderBuffer.hasRemaining()) {

                messageHeaderBuffer.flip();

                //parse header
                unfinishedMessageType = messageHeaderBuffer.get(0);
                unfinishedMessageFormat = messageHeaderBuffer.get(RawMessage.TYPE_SIZE);
                unfinishedMessageLength = messageHeaderBuffer.getInt(RawMessage.TYPE_SIZE + RawMessage.FORMAT_SIZE);

                //messageHeaderBuffer.reset();

                unfinishedMessageValueBuffer = ByteBuffer.allocate(unfinishedMessageLength);

            }

        }

        //read bytes of message value if we have already read the header
        if(!messageHeaderBuffer.hasRemaining() && unfinishedMessageValueBuffer.hasRemaining()) {
            socket.read(unfinishedMessageValueBuffer);

            //if the value of the message has now been completely read we can assemble and return the whole message
            if(!unfinishedMessageValueBuffer.hasRemaining()) {

                unfinishedMessageValueBuffer.flip();

                byte[] messageValue = unfinishedMessageValueBuffer.array();

                newCompleteMessages.add(new ClientRawMessage(
                    client,
                    unfinishedMessageType,
                    unfinishedMessageFormat,
                    unfinishedMessageLength,
                    messageValue
                ));

                messageHeaderBuffer.reset();
                unfinishedMessageValueBuffer = null;

            }

        }

        return newCompleteMessages;
    }

}
