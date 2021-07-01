package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.gui.GuiClientManager;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.FileManager;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourcesRepositioning extends AnchorPane implements View {

    public final BiConsumer<Set<ClientResourceStorageRepresentation>, Map<ResourceType, Integer>> onRepositioningDone;

    @FXML
    GridPane dashboardContainer;

    @FXML
    GridPane bottomContainer;

    @FXML
    Button confirmButton;

    final boolean canConfirmWithResourcesLeftInTempStorage;

    GuiClientManager clientManager;

    Player dashboardPlayer;
    ClientPlayerContextRepresentation playerContext;

    BooleanProperty isConfirmButtonVisible;


    public ResourcesRepositioning(
        Player dashboardPlayer,
        boolean canConfirmWithResourcesLeftInTempStorage,
        BiConsumer<Set<ClientResourceStorageRepresentation>, Map<ResourceType, Integer>> onRepositioningDone
    ) {
        clientManager = GuiClientManager.getInstance();

        this.onRepositioningDone = onRepositioningDone;
        this.dashboardPlayer = dashboardPlayer;
        this.canConfirmWithResourcesLeftInTempStorage = canConfirmWithResourcesLeftInTempStorage;
        this.playerContext = clientManager.getGameContextRepresentation().getPlayerContext(dashboardPlayer);

        isConfirmButtonVisible = new SimpleBooleanProperty();

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

        playerContext.getTempStorage().subscribe(this);

        Dashboard dashboard = new Dashboard(dashboardPlayer, true);
        GridPane.setRowIndex(dashboard, 0);
        GridPane.setColumnIndex(dashboard, 0);
        dashboardContainer.getChildren().add(dashboard);

        Storage storageComp = new Storage("PTempStorage", playerContext.getTempStorage(), true);
        GridPane.setRowIndex(storageComp, 0);
        GridPane.setColumnIndex(storageComp, 1);
        bottomContainer.getChildren().add(storageComp);

        confirmButton.visibleProperty().bind(isConfirmButtonVisible);
        confirmButton.setOnMouseClicked(obv -> onRepositioningDone.accept(
            Stream.concat(
                playerContext.getShelves().stream(),
                playerContext.getActiveLeaderCards().stream()
                    .flatMap(leaderCard -> leaderCard.getResourceStorages().stream())
            ).collect(Collectors.toSet()),
            playerContext.getTempStorage().getResources()
        ));

        updateView();

    }

    @Override
        public void updateView() {
        Platform.runLater(() -> {
            isConfirmButtonVisible.setValue(canConfirmWithResourcesLeftInTempStorage ||
                playerContext.getTempStorage().getResources().values().stream().mapToInt(n -> n).sum() == 0);
        });
    }

    @Override
    public void destroyView() {
        playerContext.getTempStorage().unsubscribe(this);
    }

}
