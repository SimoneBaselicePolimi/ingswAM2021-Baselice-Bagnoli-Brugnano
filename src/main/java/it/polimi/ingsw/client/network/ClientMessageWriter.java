package it.polimi.ingsw.client.network;

import it.polimi.ingsw.server.network.RawMessage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientMessageWriter {

    SocketChannel socket;

    ByteBuffer headerBuffer = ByteBuffer.allocate(
        RawMessage.TYPE_SIZE + RawMessage.FORMAT_SIZE + RawMessage.LENGTH_SIZE
    );

    ByteBuffer valueBuffer = null;

    public ClientMessageWriter(SocketChannel socket) {
        this.socket = socket;
    }

    public synchronized void writeMessage(RawMessage messageToSend) throws IOException {

        headerBuffer.put(messageToSend.type);
        headerBuffer.put(messageToSend.valueFormat);
        headerBuffer.putInt(messageToSend.valueLength);
        headerBuffer.flip();
        socket.write(headerBuffer);

        valueBuffer = ByteBuffer.allocate(messageToSend.valueLength);
        valueBuffer.put(messageToSend.value);
        valueBuffer.flip();
        socket.write(valueBuffer);

        headerBuffer.clear();
        valueBuffer = null;

    }

}
