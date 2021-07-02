package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.FileManager;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiClientManager extends ClientManager {

    public final double WINDOW_WIDTH = 1200;

    public final double WINDOW_HEIGHT = 780;

    protected Stage mainStage;

    public static GuiClientManager initializeInstance(MessageSender serverSender) {
        instance = new GuiClientManager(serverSender);
        return (GuiClientManager) instance;
    }

    public static GuiClientManager getInstance() throws NullPointerException {
        if (instance != null)
            return (GuiClientManager) instance;
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
        loadScene(path, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public void loadScene(String path, double customWindowWidth, double customWindowHeight) {
        Platform.runLater(() -> {
            Parent parent = null;
            try {
                parent = new FXMLLoader().load(
                    FileManager.getFileManagerInstance().loadFileFXML(path)
                );
                Scene scene = new Scene(new StackPane(parent), customWindowWidth, customWindowHeight);
                mainStage.setScene(scene);
                mainStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void loadScene(Parent component) {
        loadScene(component, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public void loadScene(Parent component, double customWindowWidth, double customWindowHeight) {
        Platform.runLater(() -> {
            Scene scene = new Scene(new StackPane(component), customWindowWidth, customWindowHeight);
            mainStage.setScene(scene);
            mainStage.show();
        });
    }

    @Override
    public void onAnotherPlayerDisconnected(Player player) {

    }

}
