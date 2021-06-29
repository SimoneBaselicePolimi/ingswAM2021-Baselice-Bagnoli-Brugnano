package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientDevelopmentCardColourRequirementRepresentation;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.IOException;

public class ColourRequirement extends AnchorPane {

    @FXML
    HBox colourReq;

    final ClientDevelopmentCardColourRequirementRepresentation colourRequirement;

    public ColourRequirement(ClientDevelopmentCardColourRequirementRepresentation colourRequirement) {
        this.colourRequirement = colourRequirement;

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/ColourRequirement.fxml")
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
        numOfCardsLabel.textProperty().setValue(String.valueOf(colourRequirement.getNumberOfCards()));

        Label flagLabel = new Label();
        flagLabel.setPrefHeight(25);
        flagLabel.setPrefWidth(40);
        flagLabel.setStyle(String.format(
            "-fx-background-color: \"#%02X%02X%02X\"",
            colourRequirement.getCardColour().getUIColour().r,
            colourRequirement.getCardColour().getUIColour().g,
            colourRequirement.getCardColour().getUIColour().b
        ));

        colourReq.getChildren().addAll(numOfCardsLabel, flagLabel);
    }

}
