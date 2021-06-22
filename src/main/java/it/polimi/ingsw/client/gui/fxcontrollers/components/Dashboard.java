package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.gui.GuiClientManager;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Dashboard extends AnchorPane {

    final Player dashboardPlayer;
    final ClientPlayerContextRepresentation playerContext;
    final GuiClientManager clientManager;

    @FXML
    VBox storagesContainer;

    @FXML
    VBox baseProductionsContainer;

    public Dashboard(Player dashboardPlayer) {
        this.dashboardPlayer = dashboardPlayer;
        clientManager = GuiClientManager.getInstance();
        playerContext = clientManager.getGameContextRepresentation().getPlayerContext(dashboardPlayer);

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/Dashboard.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @FXML
    private void initialize() {
        //storagesContainer.getChildren().addAll();
    }
}
