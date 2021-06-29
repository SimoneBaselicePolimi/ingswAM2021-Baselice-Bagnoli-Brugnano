package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientDevelopmentCardColourAndLevelRequirementRepresentation;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;

public class ColourAndLevelRequirement extends AnchorPane {

    @FXML
    HBox colourAndLevelReq;

    final ClientDevelopmentCardColourAndLevelRequirementRepresentation colourAndLevelRequirement;

    public ColourAndLevelRequirement(ClientDevelopmentCardColourAndLevelRequirementRepresentation colourAndLevelRequirement) {
        this.colourAndLevelRequirement = colourAndLevelRequirement;

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/ColourAndLevelRequirement.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        Label numOfCardsLabel = new Label();
        numOfCardsLabel.setMaxHeight(20);
        numOfCardsLabel.setFont(new Font(20));
        numOfCardsLabel.textProperty().setValue(String.valueOf(colourAndLevelRequirement.getNumberOfCards()));

        Label flagLabel = new Label();
        flagLabel.setTextFill(Color.WHITE);
        flagLabel.setText(String.valueOf(colourAndLevelRequirement.getCardLevel().toValue()));
        flagLabel.setFont(new Font(20));
        flagLabel.setAlignment(Pos.CENTER);
        flagLabel.setPrefHeight(25);
        flagLabel.setPrefWidth(40);
        flagLabel.setStyle(String.format(
            "-fx-background-color: \"#%02X%02X%02X\"",
            colourAndLevelRequirement.getCardColour().getUIColour().r,
            colourAndLevelRequirement.getCardColour().getUIColour().g,
            colourAndLevelRequirement.getCardColour().getUIColour().b
        ));

        colourAndLevelReq.getChildren().addAll(numOfCardsLabel, flagLabel);
    }

}
