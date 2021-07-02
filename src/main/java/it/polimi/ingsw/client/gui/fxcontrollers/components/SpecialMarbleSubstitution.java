package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientWhiteMarbleSubstitutionRepresentation;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.IOException;

public class SpecialMarbleSubstitution extends AnchorPane {

    @FXML
    HBox substitutionContainer;

    final ClientWhiteMarbleSubstitutionRepresentation marbleSubstitution;

    public SpecialMarbleSubstitution(ClientWhiteMarbleSubstitutionRepresentation marbleSubstitution) {
        this.marbleSubstitution = marbleSubstitution;

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/SpecialMarbleSubstitution.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        ImageView specialMarbleIcon = new ImageView();
        specialMarbleIcon.setImage(new Image(FileManager.getFileManagerInstance().loadFXImage(
            "whiteMarble.png"
        )));
        specialMarbleIcon.setFitHeight(40);
        specialMarbleIcon.setPreserveRatio(true);
        specialMarbleIcon.setSmooth(true);
        specialMarbleIcon.setCache(true);

        Label equalsLabel = new Label();
        equalsLabel.setMaxHeight(40);
        equalsLabel.setFont(new Font(40));
        equalsLabel.textProperty().setValue(" = ");

        ImageView resourceIcon = new ImageView();
        resourceIcon.setImage(new Image(FileManager.getFileManagerInstance().loadFXImage(
            marbleSubstitution.getResourceTypeToSubstitute().getIconPathForResourceType()))
        );
        resourceIcon.setFitHeight(40);
        resourceIcon.setPreserveRatio(true);
        resourceIcon.setSmooth(true);
        resourceIcon.setCache(true);

        substitutionContainer.getChildren().addAll(specialMarbleIcon, equalsLabel, resourceIcon);
    }

}
