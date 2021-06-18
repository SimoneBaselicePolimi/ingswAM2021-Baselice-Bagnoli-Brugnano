package it.polimi.ingsw.client.gui.fxcontrollers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class GameScene extends AbstractController {

    @FXML
    public AnchorPane commonComponentsContainer;

    @FXML
    public AnchorPane specificComponentsContainer;

    public final int sceneNumber;

    public GameScene(int sceneNumber) {
        this.sceneNumber = sceneNumber;
    }

    @FXML
    private void initialize() {

        GameSceneSelector commonComponentsSelector = new GameSceneSelector(
            List.of(
                new GameSceneSelector.Selection("Market", "market.fxml"),
                new GameSceneSelector.Selection("FaithPath", "faithPath.fxml"),
                new GameSceneSelector.Selection("Table", "table.fxml")
            ),
            sceneNumber
        );
        commonComponentsContainer.getChildren().add(commonComponentsSelector);


        GameSceneSelector specificComponentsSelector = new GameSceneSelector(
            List.of(
                new GameSceneSelector.Selection("dashboard", "playerDashboard.fxml")
            ),
            sceneNumber,
            commonComponentsSelector.getToggleGroup()
        );
        specificComponentsContainer.getChildren().add(specificComponentsSelector);

    }



}
