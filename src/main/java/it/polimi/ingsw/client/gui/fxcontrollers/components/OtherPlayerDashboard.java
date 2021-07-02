package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.gui.fxcontrollers.GameScene;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OtherPlayerDashboard extends GameScene {

    AnchorPane root;

    @FXML
    AnchorPane dashboardContainer;

    Player dashboardPlayer;

    public OtherPlayerDashboard(
        Player dashboardPlayer,
        int sceneNumber
    ) {
        super(sceneNumber);

        this.dashboardPlayer = dashboardPlayer;

        FXMLLoader fxmlLoader = new FXMLLoader();

        root = new AnchorPane();
        fxmlLoader.setRoot(root);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/OtherPlayerDashboard.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @Override
    protected void initialize() {
        super.initialize();

        dashboardContainer.getChildren().add(new Dashboard(dashboardPlayer, false));
    }

    public AnchorPane getRoot() {
        return root;
    }
}
