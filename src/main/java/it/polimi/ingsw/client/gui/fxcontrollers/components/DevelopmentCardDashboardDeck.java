package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientPlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.utils.Colour;
import it.polimi.ingsw.utils.FileManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

public class DevelopmentCardDashboardDeck extends AnchorPane implements View {

    @FXML
    GridPane deckContainer;

    @FXML
    VBox flagsContainer;

    DevelopmentCard developmentCardComp = null;

    ClientPlayerOwnedDevelopmentCardDeckRepresentation deck;

    ClientDevelopmentCardRepresentation cardOnTop = null;

    Consumer<ClientDevelopmentCardRepresentation> onDeckSelected;

    public DevelopmentCardDashboardDeck(
        ClientPlayerOwnedDevelopmentCardDeckRepresentation deck
    ) {
        this(deck, c -> {} );
    }

    public DevelopmentCardDashboardDeck(
        ClientPlayerOwnedDevelopmentCardDeckRepresentation deck,
        Consumer<ClientDevelopmentCardRepresentation> onDeckSelected
    ) {
        this.deck = deck;
        this.onDeckSelected = onDeckSelected;

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/DevelopmentCardDashboardDeck.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setOnDeckSelected(Consumer<ClientDevelopmentCardRepresentation> onDeckSelected) {
        this.onDeckSelected = onDeckSelected;
    }

    @FXML
    private void initialize() {
        deck.subscribe(this);
        this.setOnMouseClicked(e -> onDeckSelected.accept(cardOnTop));
        drawDeck();
    }

    public void setCardBordersColour(Colour colour){
        deckContainer.setStyle(String.format(
            "-fx-border-color:#%02x%02x%02x; -fx-border-width: 2; -fx-padding: 6px; -fx-border-radius: 8;",
            colour.r,
            colour.g,
            colour.b
        ));
    }

    public void removeCardBorders(){
        deckContainer.setStyle(
            "-fx-border-color: black; -fx-border-width: 2; -fx-padding: 6px; -fx-border-radius: 8;"
        );
    }


    void drawDeck() {
        if ((cardOnTop == null || !cardOnTop.equals(deck.peek())) && deck.numberOfCardsInDeck() > 0) {
            if (developmentCardComp != null)
                deckContainer.getChildren().remove(developmentCardComp);
            cardOnTop = deck.peek();
            developmentCardComp = new DevelopmentCard(cardOnTop);
            GridPane.setRowIndex(developmentCardComp, 0);
            GridPane.setColumnIndex(developmentCardComp, 0);
            deckContainer.getChildren().add(developmentCardComp);
        }
        flagsContainer.getChildren().clear();
        deck.getCardDeck().stream()
            .filter(c -> !c.equals(cardOnTop))
            .forEach(c -> {
                Label flagLabel = new Label();
                flagLabel.setTextFill(Color.WHITE);
                flagLabel.setText(String.valueOf(c.getLevel().toValue()));
                flagLabel.setAlignment(Pos.CENTER);
                flagLabel.setPrefHeight(7);
                flagLabel.setPrefWidth(85);
                flagLabel.setStyle(String.format(
                    "-fx-background-color: \"#%02X%02X%02X\"",
                    c.getColour().getUIColour().r,
                    c.getColour().getUIColour().g,
                    c.getColour().getUIColour().b
                ));
                flagsContainer.getChildren().add(flagLabel);
            });
    }

    @Override
    public void updateView() {
        Platform.runLater(this::drawDeck);
    }

    public Optional<DevelopmentCard> getLeaderCardOnTopComp() {
        if(developmentCardComp != null)
            return Optional.of(developmentCardComp);
        else
            return Optional.empty();
    }

    @Override
    public void destroyView() {
        deck.unsubscribe(this);
    }
}
