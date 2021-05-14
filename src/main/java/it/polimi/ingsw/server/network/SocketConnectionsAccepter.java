package it.polimi.ingsw.server.network;

import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Queue;

public class SocketConnectionsAccepter implements Runnable{

    protected final ProjectLogger logger = ProjectLogger.getLogger();

    private int tcpPort;

    private ServerSocketChannel serverSocket;

    private Queue<SocketChannel> socketQueue;

    public SocketConnectionsAccepter(int tcpPort, Queue<SocketChannel> socketQueue)  {
        this.tcpPort = tcpPort;
        this.socketQueue = socketQueue;
    }

    public void run() {

        try {
            this.serverSocket = ServerSocketChannel.open();
            this.serverSocket.bind(new InetSocketAddress(tcpPort));
        } catch(IOException e){
            logger.log(
                LogLevel.ERROR,
                "Error while trying to open a new socket to listen for incoming " +
                "connections. [TCP PORT: %s]",
                tcpPort
            );
            logger.log(e);
            return;
        }

        while(true){
            try{
                SocketChannel socketChannel = this.serverSocket.accept();

                System.out.println("Socket accepted: " + socketChannel);

                this.socketQueue.add(socketChannel);

            } catch(IOException e){
                logger.log(e);
            }
        }

    }

}