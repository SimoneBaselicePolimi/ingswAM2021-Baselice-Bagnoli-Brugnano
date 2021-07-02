package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCoveredCardsDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.utils.Colour;
import it.polimi.ingsw.utils.FileManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.function.Consumer;

public class DevelopmentCardTableDeck extends AnchorPane implements View {

    @FXML
    GridPane deckContainer;

    @FXML
    Label contentLabel;

    DevelopmentCard developmentCardComp = null;

    ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation> deck;

    ClientDevelopmentCardRepresentation cardOnTop = null;

    Consumer<ClientDevelopmentCardRepresentation> onDeckSelected;

    public DevelopmentCardTableDeck(
        ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation> deck,
        Consumer<ClientDevelopmentCardRepresentation> onDeckSelected
    ) {
        this.deck = deck;
        this.onDeckSelected = onDeckSelected;

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/DevelopmentCardTableDeck.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        deck.subscribe(this);
        this.setOnMouseClicked(e -> onDeckSelected.accept(cardOnTop));
        updateView();

    }

    public void setCardBordersColour(Colour colour){
        if(cardOnTop != null)
            developmentCardComp.setBordersColour(colour);
    }

    public void setDefaultBordersColour(){
        if(cardOnTop != null)
            developmentCardComp.setDefaultBordersColour();
    }

    @Override
    public void updateView() {
        Platform.runLater(() -> {
            if (cardOnTop == null || !cardOnTop.equals(deck.getCardOnTop())) {
                if (developmentCardComp != null)
                    deckContainer.getChildren().remove(developmentCardComp);
                cardOnTop = deck.getCardOnTop();
                developmentCardComp = new DevelopmentCard(cardOnTop);
                GridPane.setRowIndex(developmentCardComp, 0);
                GridPane.setColumnIndex(developmentCardComp, 0);
                deckContainer.getChildren().add(developmentCardComp);
            }
            contentLabel.setText(Localization.getLocalizationInstance().getString(
                "client.gui.devCardTableDeck.deckSize",
                deck.getNumberOfCardsInDeck()
            ));
        });
    }

    @Override
    public void destroyView() {
        deck.unsubscribe(this);
    }
}
