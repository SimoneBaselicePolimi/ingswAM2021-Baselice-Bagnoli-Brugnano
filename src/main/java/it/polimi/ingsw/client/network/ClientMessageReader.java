package it.polimi.ingsw.client.network;

import it.polimi.ingsw.server.network.RawMessage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientMessageReader {

    SocketChannel socket;

    ByteBuffer headerBuffer = ByteBuffer.allocate(
        RawMessage.TYPE_SIZE + RawMessage.FORMAT_SIZE + RawMessage.LENGTH_SIZE
    );

    ByteBuffer valueBuffer = null;

    public ClientMessageReader(SocketChannel socket) {
        this.socket = socket;
    }

    public RawMessage readMessage() throws IOException {

        socket.read(headerBuffer);
        headerBuffer.flip();
        byte messageType = headerBuffer.get();
        byte messageFormat = headerBuffer.get();
        int messageLength = headerBuffer.getInt();
        valueBuffer = ByteBuffer.allocate(messageLength);

        socket.read(valueBuffer);
        valueBuffer.flip();
        byte[] messageValue = valueBuffer.array();

        RawMessage rawMessageFromServer = new RawMessage(
            messageType,
            messageFormat,
            messageLength,
            messageValue
        );

        headerBuffer.clear();
        valueBuffer = null;

        return rawMessageFromServer;
    }

}
