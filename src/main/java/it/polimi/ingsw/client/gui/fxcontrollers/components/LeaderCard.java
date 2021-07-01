package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.utils.Colour;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class LeaderCard extends AnchorPane implements View {

    @FXML
    GridPane bordersGrid;

    @FXML
    Label title;

    @FXML
    Label requirementsTitle;

    @FXML
    Label victoryPointsLabel;

    @FXML
    HBox requirementsContainer;

    @FXML
    VBox specialPowersContainer;

    @FXML
    ScrollPane specialPowersScrollPane;

    ClientLeaderCardRepresentation card;

    Set<Production> productionsComp = new HashSet<>();

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

        title.setText(Localization.getLocalizationInstance().getString("leaderCards.name"));
        title.setFont(new Font(22));

        requirementsTitle.setText(Localization.getLocalizationInstance().getString(
            "leaderCards.requirements.requirements"
        ));
        requirementsTitle.setFont(new Font(17));

        victoryPointsLabel.setText(Localization.getLocalizationInstance().getString(
            "leaderCards.victoryPoints",
            card.getVictoryPoints()
        ));
        victoryPointsLabel.setFont(new Font(15));

        card.getRequirements().forEach(r -> {
            requirementsContainer.getChildren().add(r.buildGuiComponent());
        });

        card.getProductions().forEach(p -> {
            Production prod = new Production(p);
            specialPowersContainer.getChildren().add(prod);
            productionsComp.add(prod);

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

        card.subscribe(this);
        updateView();
    }

    public Set<Production> getProductionsComp() {
        return new HashSet<>(productionsComp);
    }

    @Override
    public void updateView() {
        if(card.getState().equals(LeaderCardState.ACTIVE)) {
            bordersGrid.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            specialPowersContainer.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            specialPowersScrollPane.setStyle("-fx-background: WHITE; -fx-border-color: WHITE");
        } else if(card.getState().equals(LeaderCardState.HIDDEN)) {
            bordersGrid.setBackground(new Background(new BackgroundFill(Color.DIMGREY, CornerRadii.EMPTY, Insets.EMPTY)));
            specialPowersContainer.setBackground(new Background(new BackgroundFill(Color.DIMGREY, CornerRadii.EMPTY, Insets.EMPTY)));
            specialPowersScrollPane.setStyle("-fx-background: DIMGREY; -fx-border-color: DIMGREY");
        } else {
            bordersGrid.setBackground(new Background(new BackgroundFill(Color.DARKRED, CornerRadii.EMPTY, Insets.EMPTY)));
            specialPowersContainer.setBackground(new Background(new BackgroundFill(Color.DARKRED, CornerRadii.EMPTY, Insets.EMPTY)));
            specialPowersScrollPane.setStyle("-fx-background: DARKRED; -fx-border-color: DARKRED");
        }
    }

    @Override
    public void destroyView() {
        card.unsubscribe(this);
    }
}
