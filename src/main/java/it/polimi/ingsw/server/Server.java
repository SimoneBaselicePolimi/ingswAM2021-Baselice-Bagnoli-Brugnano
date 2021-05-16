package it.polimi.ingsw.server;

import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.server.network.ClientRawMessageProcessor;
import it.polimi.ingsw.server.network.NetworkLayer;
import it.polimi.ingsw.server.network.SocketConnectionsAccepter;
import it.polimi.ingsw.server.network.SocketConnectionsProcessor;

import java.io.IOException;
import java.util.Arrays;

public class Server {

    protected static final ProjectLogger logger = ProjectLogger.getLogger();

    private static final int TCP_SERVER_PORT = 11056;

    public static void main( String[] args ) {
        startServer();

    }

    public static void startServer() {

        ClientRawMessageProcessor clientRawMessageProcessor = new ClientRawMessageProcessor(
            client -> logger.log(LogLevel.INFO, "new conn %s", client.getClientId()),
            message -> logger.log(LogLevel.INFO, Arrays.toString(message.value)),
            client -> logger.log(LogLevel.INFO, "client disconnected %s", client.getClientId())
        );

        NetworkLayer net = new NetworkLayer(TCP_SERVER_PORT, clientRawMessageProcessor);
        try {
            net.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
