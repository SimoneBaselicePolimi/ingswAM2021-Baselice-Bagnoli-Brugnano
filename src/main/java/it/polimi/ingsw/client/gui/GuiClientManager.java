package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.utils.FileManager;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.management.InstanceNotFoundException;
import java.io.IOException;

public class GuiClientManager extends ClientManager {

    public final double WINDOW_WIDTH = 1024;

    public final double WINDOW_HEIGHT = 576;

    protected static GuiClientManager instance = null;

    protected Stage mainStage;

    public static GuiClientManager initializeInstance(MessageSender serverSender) {
        instance = new GuiClientManager(serverSender);
        return instance;
    }

    public static GuiClientManager getInstance() throws NullPointerException {
        if (instance != null)
            return instance;
        else
            throw new NullPointerException();
    }

    protected GuiClientManager(MessageSender serverSender) {
        super(serverSender);
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void loadScene(String path) {
        Platform.runLater(() -> {
            Parent parent = null;
            try {
                parent = new FXMLLoader().load(
                    FileManager.getFileManagerInstance().loadFileFXML(path)
                );
                Scene scene = new Scene(new StackPane(parent), WINDOW_WIDTH, WINDOW_HEIGHT);
                mainStage.setScene(scene);
                mainStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

}
