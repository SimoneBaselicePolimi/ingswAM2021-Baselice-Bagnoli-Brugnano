package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.view.View;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class GameScene extends AbstractController implements View {

    @FXML
    AnchorPane commonComponentsContainer;

    @FXML
    AnchorPane specificComponentsContainer;

    public final int sceneNumber;

    protected BooleanProperty canMyPlayerDoMainAction = new SimpleBooleanProperty(false);
    protected BooleanProperty isMyPlayerTurn = new SimpleBooleanProperty(false);

    public GameScene(int sceneNumber) {
        this.sceneNumber = sceneNumber;
    }

    @FXML
    protected void initialize() {

        clientManager.getGameContextRepresentation().subscribe(this);

        GameSceneSelector commonComponentsSelector = new GameSceneSelector(
            List.of(
                new GameSceneSelector.Selection("Market", "Market.fxml"),
                new GameSceneSelector.Selection("FaithPath", "FaithPath.fxml"),
                new GameSceneSelector.Selection("Table", "Table.fxml"),
                new GameSceneSelector.Selection("History", "GameHistory.fxml")
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

        updateView();

    }


    @Override
    public void updateView() {
        canMyPlayerDoMainAction.setValue(clientManager.getGameState().equals(GameState.MY_PLAYER_TURN_BEFORE_MAIN_ACTION));
        isMyPlayerTurn.setValue(
            clientManager.getGameState().equals(GameState.MY_PLAYER_TURN_BEFORE_MAIN_ACTION) ||
            clientManager.getGameState().equals(GameState.MY_PLAYER_TURN_AFTER_MAIN_ACTION)
        );
    }

    @Override
    public void destroyView() {
        clientManager.getGameContextRepresentation().unsubscribe(this);
    }

}
