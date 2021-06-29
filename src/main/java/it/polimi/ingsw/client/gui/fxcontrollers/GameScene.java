package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.GameState;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
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
    protected void initialize() {

        GameSceneSelector commonComponentsSelector = new GameSceneSelector(
            List.of(
                new GameSceneSelector.Selection("Market", "Market.fxml"),
                new GameSceneSelector.Selection("FaithPath", "FaithPath.fxml"),
                new GameSceneSelector.Selection("Table", "Table.fxml")
            ),
            sceneNumber
        );
        commonComponentsContainer.getChildren().add(commonComponentsSelector);

        ToggleGroup toggleGroup = commonComponentsSelector.getToggleGroup();


        List<GameSceneSelector.Selection> availableScenes;

        if(clientManager.getGameState().equals(GameState.GAME_SETUP))
            availableScenes =  List.of(
                new GameSceneSelector.Selection("Begin setup", "LeaderCardsSetup.fxml")
            );
        else
            availableScenes = List.of(
                    new GameSceneSelector.Selection("Dashboard", "PlayerDashboard.fxml")
            );

        GameSceneSelector specificComponentsSelector = new GameSceneSelector(
            availableScenes,
            sceneNumber,
            toggleGroup
        );
        specificComponentsContainer.getChildren().add(specificComponentsSelector);

        toggleGroup.selectToggle(toggleGroup.getToggles().get(sceneNumber));

    }



}
