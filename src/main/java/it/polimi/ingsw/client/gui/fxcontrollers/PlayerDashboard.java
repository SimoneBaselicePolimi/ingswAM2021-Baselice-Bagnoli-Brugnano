package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.gui.fxcontrollers.components.Dashboard;
import it.polimi.ingsw.client.view.View;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class PlayerDashboard extends GameScene implements View {

    @FXML
    public AnchorPane dashboardContainer;

    @FXML
    public Button activateProductionsButton;

    @FXML
    public Button cancelButton;

    BooleanProperty isProductionsActivationModeEnabled =  new SimpleBooleanProperty(false);
    BooleanProperty canMyPlayerDoMainAction = new SimpleBooleanProperty(false);

    public PlayerDashboard() {
        super(4);
    }

    @FXML
    @Override
    protected void initialize() {
        super.initialize();

        Dashboard dashboard = new Dashboard(clientManager.getMyPlayer(), false);
        dashboardContainer.getChildren().add(dashboard);

        clientManager.getGameContextRepresentation().subscribe(this);

        activateProductionsButton.visibleProperty().bind(isProductionsActivationModeEnabled.not().and(canMyPlayerDoMainAction));
        activateProductionsButton.setOnMouseClicked(e -> isProductionsActivationModeEnabled.setValue(true));

        cancelButton.visibleProperty().bind(isProductionsActivationModeEnabled);
        cancelButton.setOnMouseClicked(e -> isProductionsActivationModeEnabled.setValue(false));

        isProductionsActivationModeEnabled.addListener( obv -> updateProdBorderColour());

        dashboard.getAllProductionsComp();

        updateView();

    }

    void updateProdBorderColour() {

    }

    @Override
    public void updateView() {
        canMyPlayerDoMainAction.setValue(clientManager.getGameState().equals(GameState.MY_PLAYER_TURN_BEFORE_MAIN_ACTION));
    }

    @Override
    public void destroyView() {
        clientManager.getGameContextRepresentation().unsubscribe(this);
    }

}
