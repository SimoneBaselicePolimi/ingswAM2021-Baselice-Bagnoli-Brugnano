package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.client.network.ClientNetworkLayer;
import it.polimi.ingsw.client.network.ClientNotConnectedException;
import it.polimi.ingsw.client.servermessage.ServerMessage;
import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.network.NetworkProto;
import it.polimi.ingsw.network.PingWorker;
import it.polimi.ingsw.server.network.RawMessage;
import it.polimi.ingsw.utils.serialization.SerializationHelper;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiClient extends Application {

    protected static final ProjectLogger logger = ProjectLogger.getLogger();

    private static final int TCP_SERVER_PORT = 11056;
    private static final String TCP_SERVER_ADDRESS = "localhost";

    protected static GuiClientManager clientManager;

    public static void main(String[] args) {

        try {
            startClient();
        } catch (IOException | ClientNotConnectedException e) {
            e.printStackTrace();
        }

        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        clientManager.setMainStage(stage);
        clientManager.loadScene("PlayerRegistration.fxml");
    }

    public static void startClient() throws IOException, ClientNotConnectedException {

        //ProjectLogger.getLogger().setLogInConsole(false);

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
            } catch (ClientNotConnectedException | IOException e) { //TODO
                e.printStackTrace();
            }
        };

        clientManager = GuiClientManager.initializeInstance(messageSender);

        PingWorker pingWorker = new PingWorker(
            rawMessage -> {
                try {
                    networkLayer.sendMessage(rawMessage);
                } catch (ClientNotConnectedException e) {
                    logger.log(LogLevel.ERROR, "Unexpected error while trying to send a ping message");
                } catch (IOException e) {
                    logger.log(LogLevel.ERROR, "Unexpected error while trying to send a ping message");
                    logger.log(e);
                }
            },
            clientManager::onConnectionWithServerDropped
        );

        networkLayer.setMessageFromServerProcessingPolicy(messageFromServer -> {
            if (messageFromServer.type == NetworkProto.MESSAGE_TYPE.PING_MESSAGE) {
                pingWorker.handlePingMessage(messageFromServer);
            } else if(messageFromServer.type == NetworkProto.MESSAGE_TYPE.GAME_MESSAGE) {
                try {
                    ServerMessage deserializedMessage = SerializationHelper.deserializeYamlFromBytes(
                        messageFromServer.value,
                        ServerMessage.class,
                        clientManager.getContextInfoMap()
                    );
                    clientManager.handleServerMessage(deserializedMessage);
                } catch (IOException e) {
                    logger.log(e);
                }
            } else {
                logger.log(LogLevel.ERROR, "Message of unexpected type code [%s]", String.valueOf(messageFromServer.type));
            }
        });

        networkLayer.start();

        pingWorker.start();

    }

}