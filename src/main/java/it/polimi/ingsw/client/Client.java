package it.polimi.ingsw.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.client.cli.ConsoleWriter;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.view.PreGameView;
import it.polimi.ingsw.client.network.ClientNetworkLayer;
import it.polimi.ingsw.client.network.ClientNotConnectedException;
import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.network.NetworkProto;
import it.polimi.ingsw.client.servermessage.ServerMessage;
import it.polimi.ingsw.server.network.RawMessage;
import it.polimi.ingsw.utils.serialization.SerializationHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class
Client {

    protected static final ProjectLogger logger = ProjectLogger.getLogger();

    private static final int TCP_SERVER_PORT = 11056;
    private static final String TCP_SERVER_ADDRESS = "localhost";

    protected BufferedReader userInputReader;
    protected ConsoleWriter consoleWriter;

    public Client(BufferedReader userInputReader, ConsoleWriter consoleWriter) {
        this.userInputReader = userInputReader;
        this.consoleWriter = consoleWriter;
    }

    public Client() {
        this.userInputReader = new BufferedReader(new InputStreamReader(System.in));
        this.consoleWriter = new ConsoleWriter() {
            @Override
            public void writeToConsole(String text) {
                System.out.print(text);
            }

            @Override
            public void writeNewLineToConsole(String line) {
                System.out.println(line);
            }
        };
    }

    public static void main( String[] args ) {

        try {
            new Client().startClient();
        } catch (IOException | ClientNotConnectedException e) {
            e.printStackTrace();
        }


    }

    public void startClient() throws IOException, ClientNotConnectedException {

        ClientNetworkLayer networkLayer = new ClientNetworkLayer(
            TCP_SERVER_ADDRESS,
            TCP_SERVER_PORT
        );

        MessageSender messageSender = messageForServer -> {
            try {
                byte[] messageContent = SerializationHelper.serializeYamlAsBytes(messageForServer);
                networkLayer.sendMessage(new RawMessage(
                    NetworkProto.MESSAGE_TYPE.GAME_MESSAGE,
                    NetworkProto.MESSAGE_FORMAT.YAML,
                    messageContent.length,
                    messageContent
                ));
            } catch (JsonProcessingException e) { //TODO
                e.printStackTrace();
            } catch (ClientNotConnectedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        CliClientManager clientManager = new CliClientManager(consoleWriter, messageSender);

        networkLayer.setMessageFromServerProcessingPolicy(messageFromServer -> {
            try {
                ServerMessage deserializedMessage = SerializationHelper.deserializeYamlFromBytes(
                    messageFromServer.value,
                    ServerMessage.class,
                    clientManager.getContextInfoMap()
                );
                clientManager.handleServerMessage(deserializedMessage);
            } catch (IOException e) {
                //TODO
                e.printStackTrace();
            }
        });

        networkLayer.start();

        new PreGameView(clientManager, clientManager.getConsoleDisplayHeight(), clientManager.getConsoleDisplayWidth());


        while (true) {
            String input = getUserInputBlocking();
            clientManager.handleUserInput(input);
        }

    }

    protected String getUserInputBlocking() throws IOException {
        return userInputReader.readLine();
    }

    @Deprecated
    public void startTestClient() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 11056));

        while(!socketChannel.finishConnect());

        logger.log(LogLevel.INFO, socketChannel.isConnected() ? "ok" : "ko");

        ByteBuffer h = ByteBuffer.allocate(1 + 1 + 4);
        ByteBuffer s = ByteBuffer.allocate(10);
        Byte b = 0;
        Byte f = 0;
        int len = s.limit();
        h.put(b);
        h.put(f);
        h.putInt(len);
        s.put(new byte[]{1,2,3,4,5,6,7,8,9,10});
        h.flip();
        s.flip();
        while (h.hasRemaining())
            socketChannel.write(h);
        while (s.hasRemaining())
            socketChannel.write(s);
        h.flip();
        s.flip();
        while (h.hasRemaining())
            socketChannel.write(h);
        while (s.hasRemaining())
            socketChannel.write(s);
        logger.log(LogLevel.INFO, "written!!!!");

        ByteBuffer h2 = ByteBuffer.allocate(1 + 1 + 4);
        ByteBuffer r = ByteBuffer.allocate(4);
        while (h2.hasRemaining())
            socketChannel.read(h2);
        while (r.hasRemaining())
            socketChannel.read(r);
        r.flip();
        byte[] cont = r.array();
        logger.log(LogLevel.INFO, Arrays.toString(cont));
    }

}
