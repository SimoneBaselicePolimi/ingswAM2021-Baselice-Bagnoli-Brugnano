package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.utils.Colour;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.io.IOException;

public class DevelopmentCard extends AnchorPane {

    @FXML
    Label contentLabel;

    @FXML
    GridPane bordersGrid;

    ClientDevelopmentCardRepresentation card;

    public DevelopmentCard(ClientDevelopmentCardRepresentation card) {
        this.card = card;
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/DevelopmentCard.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    public void setBordersColour(Colour colour){
        bordersGrid.setStyle(String.format(
            "-fx-border-color:#%02x%02x%02x; -fx-border-width: 3; -fx-border-style: solid;",
            colour.r,
            colour.g,
            colour.b
        ));
        bordersGrid.toFront();
    }

    public void removeBorders(){
        bordersGrid.setStyle("-fx-border-width: 0;");
    }

    public ClientDevelopmentCardRepresentation getLeaderCardRepresentation() {
        return card;
    }

}
