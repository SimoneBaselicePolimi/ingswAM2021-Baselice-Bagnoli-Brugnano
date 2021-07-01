package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.gui.fxcontrollers.components.Dashboard;
import it.polimi.ingsw.client.gui.fxcontrollers.components.Production;
import it.polimi.ingsw.client.view.View;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.util.HashSet;

public class PlayerDashboard extends GameScene implements View {

    @FXML
    AnchorPane dashboardContainer;

    @FXML
    Button activateProductionsButton;

    @FXML
    Button cancelButton;

    BooleanProperty isProductionsActivationModeEnabled =  new SimpleBooleanProperty(false);
    SetProperty<Production> selectedProds = new SimpleSetProperty<>(FXCollections.observableSet(new HashSet<>()));

    public PlayerDashboard() {
        super(4);
    }

    @FXML
    @Override
    protected void initialize() {
        super.initialize();

        Dashboard dashboard = new Dashboard(clientManager.getMyPlayer(), false);
        dashboardContainer.getChildren().add(dashboard);

        activateProductionsButton.visibleProperty().bind(isProductionsActivationModeEnabled.not().and(canMyPlayerDoMainAction));
        activateProductionsButton.setOnMouseClicked(e -> isProductionsActivationModeEnabled.setValue(true));

        cancelButton.visibleProperty().bind(isProductionsActivationModeEnabled);
        cancelButton.setOnMouseClicked(e -> {
            isProductionsActivationModeEnabled.setValue(false);
            selectedProds.clear();
        });

        isProductionsActivationModeEnabled.addListener( obv -> updateProdBorderColour());

        dashboard.getAllProductionsComp();

        updateView();

    }

    void updateProdBorderColour() {
        //if(isProductionsActivationModeEnabled)
    }

    @Override
    public void updateView() {
        super.updateView();
    }

    @Override
    public void destroyView() {
        super.destroyView();
        clientManager.getGameContextRepresentation().unsubscribe(this);
    }

}
