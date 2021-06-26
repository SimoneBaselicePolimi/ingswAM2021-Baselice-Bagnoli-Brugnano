package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.gui.GuiClientManager;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class ResourcesRepositioning extends AnchorPane {

    public final BiConsumer<Set<ClientResourceStorageRepresentation>, Map<ResourceType, Integer>> onRepositioningDone;

    @FXML
    GridPane dashboardContainer;

    @FXML
    GridPane bottomContainer;

    GuiClientManager clientManager;

    Player dashboardPlayer;
    ClientPlayerContextRepresentation playerContext;


    public ResourcesRepositioning(
        Player dashboardPlayer,
        BiConsumer<Set<ClientResourceStorageRepresentation>, Map<ResourceType, Integer>> onRepositioningDone
    ) {
        clientManager = GuiClientManager.getInstance();

        this.onRepositioningDone = onRepositioningDone;
        this.dashboardPlayer = dashboardPlayer;
        this.playerContext = clientManager.getGameContextRepresentation().getPlayerContext(dashboardPlayer);

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/ResourcesRepositioning.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @FXML
    private void initialize() {

        Dashboard dashboard = new Dashboard(dashboardPlayer, true);
        GridPane.setRowIndex(dashboard, 0);
        GridPane.setColumnIndex(dashboard, 0);
        dashboardContainer.getChildren().add(dashboard);

        Storage storageComp = new Storage("PTempStorage", playerContext.getTempStorage(), true);
        GridPane.setRowIndex(storageComp, 0);
        GridPane.setColumnIndex(storageComp, 1);
        bottomContainer.getChildren().add(storageComp);

    }

}
