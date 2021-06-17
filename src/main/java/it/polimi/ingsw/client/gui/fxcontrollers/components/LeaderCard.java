package it.polimi.ingsw.client.gui.fxcontrollers.components;

import com.sun.prism.paint.Color;
import com.sun.prism.paint.Paint;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeaderCard extends AnchorPane {

    @FXML
    Label contentLabel;

    @FXML
    GridPane bordersGrid;


//    @FXML
//    ImageView backgroundImg;

    ClientLeaderCardRepresentation card;

    public LeaderCard(ClientLeaderCardRepresentation card) {
        this.card = card;
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/LeaderCard.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    public void select() {
        System.out.print("Selezionata carta: " + card.getItemID());
        bordersGrid.setStyle("-fx-border-color:#ff0000; -fx-border-width: 3; -fx-border-style: solid;");
        bordersGrid.toFront();
    }

    public void deselect(){
        System.out.print("Deselezionata carta: " + card.getItemID());
        bordersGrid.setStyle("-fx-border-width: 0;");
    }


    @FXML
    private void initialize() {
        ImageView img = new ImageView();
        img.setImage(new Image(FileManager.getFileManagerInstance().loadFXImage("TestLeaderCard.png")));
        img.setFitWidth(150);
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);
        this.getChildren().add(img);
        contentLabel.setText(card.getItemID() + "\n" + card.getVictoryPoints() + "\n" + card.getState().name());
        contentLabel.toFront();
    }
}
