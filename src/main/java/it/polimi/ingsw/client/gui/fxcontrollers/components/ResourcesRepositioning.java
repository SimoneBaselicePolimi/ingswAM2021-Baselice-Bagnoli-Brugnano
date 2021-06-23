package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;

public class ResourcesRepositioning extends AnchorPane {

    public final Consumer<Set<ClientResourceStorageRepresentation>> onRepositioningDone;

    @FXML
    GridPane container;

    @FXML
    Player dashboardPlayer;

    public ResourcesRepositioning(Player dashboardPlayer, Consumer<Set<ClientResourceStorageRepresentation>> onRepositioningDone) {
        this.onRepositioningDone = onRepositioningDone;
        this.dashboardPlayer = dashboardPlayer;

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
        Dashboard dashboard = new Dashboard(dashboardPlayer);
    }

}
