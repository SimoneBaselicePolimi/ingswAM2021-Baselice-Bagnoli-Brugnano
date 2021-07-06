package it.polimi.ingsw.client.network;

import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.server.network.RawMessage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class ClientNetworkLayer extends Thread {

    protected static final ProjectLogger logger = ProjectLogger.getLogger();

    private final String tcpServerAddress;
    private final int tcpServerPort;

    protected Consumer<RawMessage> messageFromServerProcessingPolicy = m -> {};

    SocketChannel socketChannel = null;
    ClientMessageReader messageReader = null;
    ClientMessageWriter messageWriter = null;

    AtomicBoolean isNetworkReady = new AtomicBoolean(false);

    public ClientNetworkLayer(
        String tcpServerAddress,
        int tcpServerPort
    ) {
        this.tcpServerAddress = tcpServerAddress;
        this.tcpServerPort = tcpServerPort;
    }

    public ClientNetworkLayer(
        String tcpServerAddress,
        int tcpServerPort,
        Consumer<RawMessage> messageFromServerProcessingPolicy
    ) {
        this.tcpServerAddress = tcpServerAddress;
        this.tcpServerPort = tcpServerPort;
        this.messageFromServerProcessingPolicy = messageFromServerProcessingPolicy;
    }

    public void setMessageFromServerProcessingPolicy(Consumer<RawMessage> messageFromServerProcessingPolicy) {
        this.messageFromServerProcessingPolicy = messageFromServerProcessingPolicy;
    }

    public synchronized void sendMessage(RawMessage messageToSend) throws ClientNotConnectedException, IOException {
        if(socketChannel == null)
            throw new ClientNotConnectedException();
        messageWriter.writeMessage(messageToSend);
    }

    @Override
    public void run() {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(tcpServerAddress, tcpServerPort));
            while (!socketChannel.finishConnect());
            messageReader = new ClientMessageReader(socketChannel);
            messageWriter = new ClientMessageWriter(socketChannel);
            isNetworkReady.set(true);
            while (true) {
                RawMessage newMessageFromServer = messageReader.readMessage();
                messageFromServerProcessingPolicy.accept(newMessageFromServer);
            }

        } catch (IOException e) {
            ProjectLogger.getLogger().log(e);
        }
    }

    public synchronized boolean isNetworkReady() {
        return isNetworkReady.get();
    }
}
