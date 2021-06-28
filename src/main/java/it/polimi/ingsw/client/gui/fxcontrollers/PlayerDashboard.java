package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.gui.fxcontrollers.components.Dashboard;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class PlayerDashboard extends GameScene {

    @FXML
    public AnchorPane dashboardContainer;

    public PlayerDashboard() {
        super(3);
    }

    @FXML
    @Override
    protected void initialize() {
        super.initialize();
        dashboardContainer.getChildren().add(new Dashboard(clientManager.getMyPlayer(), false));

    }

}
