package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.gui.GuiClientManager;
import it.polimi.ingsw.utils.Colour;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.util.List;

public class GameSceneSelector extends AnchorPane {

    public final double BUTTON_WIDTH = 60;
    public final double BUTTON_HEIGHT = 60;
    public final double FONT_SIZE = 10;

    public static class Selection {
        public final String text;
        public final Colour backgroundColour;
        public final String scenePath;

        Selection(String text, Colour backgroundColour, String scenePath) {
            this.text = text;
            this.backgroundColour = backgroundColour;
            this.scenePath = scenePath;
        }

        public Selection(String text, String scenePath) {
            this.text = text;
            this.scenePath = scenePath;
            backgroundColour = Colour.WHITE;
        }

    }

    ToggleGroup toggleGroup;

    GuiClientManager clientManager;

    @FXML
    VBox container;

    final List<Selection> selections;
    int activeSelectionIndex;

    public GameSceneSelector(List<Selection> selections, int activeSelectionIndex, ToggleGroup toggleGroup) {
        this.selections = selections;
        this.activeSelectionIndex = activeSelectionIndex;
        this.toggleGroup = toggleGroup;
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("GameSceneSelector.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        clientManager = GuiClientManager.getInstance();

    }

    public GameSceneSelector(List<Selection> selections, int activeSelectionIndex) {
        this(selections, activeSelectionIndex, new ToggleGroup());
    }

    public ToggleGroup getToggleGroup() {
        return toggleGroup;
    }

    @FXML
    private void initialize() {

        for(int i = 0; i < selections.size(); i++) {
            Selection selection = selections.get(i);
            ToggleButton button = new ToggleButton();
            button.setPrefWidth(BUTTON_WIDTH);
            button.setPrefHeight(BUTTON_HEIGHT);
//            button.setStyle(String.format(
//                "-fx-background-color: #%02X%02X%02X",
//                selection.backgroundColour.r,
//                selection.backgroundColour.b,
//                selection.backgroundColour.g
//            ));
            Font defaultFont = button.getFont();
            button.setFont(Font.font(defaultFont.getName(), FontWeight.NORMAL, FONT_SIZE));
            button.setText(selection.text);
            button.setToggleGroup(toggleGroup);
            button.setOnMouseClicked( e -> clientManager.loadScene(selection.scenePath));
            container.getChildren().add(button);
        }

        toggleGroup.selectToggle(toggleGroup.getToggles().get(activeSelectionIndex));

    }


//    @FXML
//    public String getStringProperty() {
//        return stringProperty.get();
//    }
//
//    public StringProperty stringPropertyProperty() {
//        return stringProperty;
//    }
//
//    @FXML
//    public void setStringProperty(String stringProperty) {
//        this.stringProperty.set(stringProperty);
//    }

    //    @FXML
//    public ObservableList<String> getScenes() {
//        return scenes.get();
//    }
//
//    @FXML
//    public ListProperty<String> scenesProperty() {
//        return scenes;
//    }
//
//    @FXML
//    public void setScenes(ObservableList<String> scenes) {
//        this.scenes.set(scenes);
//    }

}
