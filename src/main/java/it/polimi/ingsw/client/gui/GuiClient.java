package it.polimi.ingsw.client.gui;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.ConsoleWriter;
import it.polimi.ingsw.client.cli.view.PreGameView;
import it.polimi.ingsw.client.gui.fxcontrollers.GameSceneSelector;
import it.polimi.ingsw.client.network.ClientNetworkLayer;
import it.polimi.ingsw.client.network.ClientNotConnectedException;
import it.polimi.ingsw.client.servermessage.ServerMessage;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.network.NetworkProto;
import it.polimi.ingsw.server.network.RawMessage;
import it.polimi.ingsw.utils.FileManager;
import it.polimi.ingsw.utils.serialization.SerializationHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

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

        //clientManager.loadScene("market.fxml");




    }

    public static void startClient() throws IOException, ClientNotConnectedException {

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

    }

}