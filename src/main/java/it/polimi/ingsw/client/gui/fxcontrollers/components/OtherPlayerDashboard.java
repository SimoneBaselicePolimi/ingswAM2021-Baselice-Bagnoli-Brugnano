package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.gui.GuiClientManager;
import it.polimi.ingsw.client.gui.fxcontrollers.GameScene;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OtherPlayerDashboard extends AnchorPane {

    @FXML
    AnchorPane commonComponentsContainer;

    @FXML
    AnchorPane specificComponentsContainer;

    public final int sceneNumber;

    @FXML
    AnchorPane dashboardContainer;

    Player dashboardPlayer;

    public OtherPlayerDashboard(
        Player dashboardPlayer,
        int sceneNumber
    ) {
        this.sceneNumber = sceneNumber;
        this.dashboardPlayer = dashboardPlayer;

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/OtherPlayerDashboard.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @FXML
    protected void initialize() {
        GameScene.initializeGameSceneSelector(
            sceneNumber,
            commonComponentsContainer,
            specificComponentsContainer,
            GuiClientManager.getInstance()
        );
        dashboardContainer.getChildren().add(new Dashboard(dashboardPlayer, false));
    }

}
