package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.gui.GuiClientManager;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class Dashboard extends AnchorPane {

    final Player dashboardPlayer;
    final ClientPlayerContextRepresentation playerContext;
    final GuiClientManager clientManager;

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



    }

}
