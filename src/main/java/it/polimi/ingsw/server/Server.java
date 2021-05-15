package it.polimi.ingsw.server;

import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.server.network.ClientRawMessageProcessor;
import it.polimi.ingsw.server.network.SocketConnectionsAccepter;
import it.polimi.ingsw.server.network.SocketConnectionsProcessor;

public class Server {

    protected static final ProjectLogger logger = ProjectLogger.getLogger();

    private final int TCP_SERVER_PORT = 0;

    public static void main( String[] args ) {


    }

    public static void startServer() {
        ClientRawMessageProcessor clientRawMessageProcessor = new ClientRawMessageProcessor(
            client -> logger.log("new conn %s", client.)
        );

    }
}
