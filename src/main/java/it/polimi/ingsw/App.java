package it.polimi.ingsw;

import it.polimi.ingsw.client.network.ClientNotConnectedException;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import static it.polimi.ingsw.client.gui.GuiClient.startClient;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        try {
            startClient();
        } catch (IOException | ClientNotConnectedException e) {
            e.printStackTrace();
        }

        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
