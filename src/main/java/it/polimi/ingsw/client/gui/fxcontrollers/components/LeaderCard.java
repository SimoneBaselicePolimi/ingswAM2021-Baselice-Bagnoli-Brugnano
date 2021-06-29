package it.polimi.ingsw.client.gui.fxcontrollers.components;

import com.sun.prism.paint.Color;
import com.sun.prism.paint.Paint;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.utils.Colour;
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
    GridPane bordersGrid;

    @FXML
    Label title;

    @FXML
    HBox requirementsContainer;

    @FXML
    VBox specialPowersContainer;

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

    public void setBorderColour(Colour colour) {
        bordersGrid.setStyle(String.format(
            "-fx-border-color:#%02x%02x%02x; -fx-border-width: 2; -fx-padding: 4px; -fx-border-radius: 8;",
            colour.r,
            colour.g,
            colour.b
        ));
        bordersGrid.toFront();
    }

    public void setDefaultBorderColour(){
        bordersGrid.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-padding: 4px; -fx-border-radius: 8;");
    }

    public ClientLeaderCardRepresentation getLeaderCardRepresentation() {
        return card;
    }

    @FXML
    private void initialize() {

        setDefaultBorderColour();

        card.getProductions().forEach(p -> {
            Production prod = new Production(p);
            specialPowersContainer.getChildren().add(prod);

        });

        card.getResourceStorages().forEach(s -> {
            Storage storage = new Storage("PRes Sto", s); //TODO LOC
            specialPowersContainer.getChildren().add(storage);
        });

        card.getWhiteMarbleSubstitutions().forEach(s -> {
            SpecialMarbleSubstitution substitution = new SpecialMarbleSubstitution(s);
            specialPowersContainer.getChildren().add(substitution);
        });

        card.getCardCostDiscounts().forEach(d -> {
            DevCardCostDiscount discount = new DevCardCostDiscount(d);
            specialPowersContainer.getChildren().add(discount);
        });

//        ImageView img = new ImageView();
//        img.setImage(new Image(FileManager.getFileManagerInstance().loadFXImage("TestLeaderCard.png")));
//        img.setFitWidth(150);
//        img.setPreserveRatio(true);
//        img.setSmooth(true);
//        img.setCache(true);
//        this.getChildren().add(img);
//        contentLabel.setText(card.getItemID() + "\n" + card.getVictoryPoints() + "\n" + card.getState().name());
//        contentLabel.toFront();
    }

}
