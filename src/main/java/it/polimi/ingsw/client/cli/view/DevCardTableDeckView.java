package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCoveredCardsDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.utils.Colour;

import java.util.ArrayList;

/**
 * View representing the deck of development cards on the game board
 */
public class DevCardTableDeckView extends CliView {

    public static final int DEV_CARD_DECK_ROW_SIZE = DevCardView.DEV_CARD_ROW_SIZE+1;
    public static final int DEV_CARD_DECK_COL_SIZE = DevCardView.DEV_CARD_COL_SIZE;

    public final DevelopmentCardColour cardColour;
    public final DevelopmentCardLevel cardLevel;

    protected final ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation> cardDeck;

    LabelView deckLabel;

    DevCardView cardView;

    ClientDevelopmentCardRepresentation card;

    public DevCardTableDeckView(
        DevelopmentCardColour cardColour,
        DevelopmentCardLevel cardLevel,
        CliClientManager clientManager
    ) {
        super(clientManager, DEV_CARD_DECK_ROW_SIZE, DEV_CARD_DECK_COL_SIZE);
        this.cardColour = cardColour;
        this.cardLevel = cardLevel;
        this.cardDeck = clientManager.getGameContextRepresentation()
            .getDevelopmentCardsTable().getDeck(cardLevel, cardColour);

        subscribeToRepresentation(cardDeck);

        deckLabel = new LabelView(new ArrayList<>(), clientManager);

        createNewDevCardAndDeckLabel();
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {

        deckLabel.setText(FormattedChar.convertStringToFormattedCharList(
            Localization.getLocalizationInstance().getString(
                "client.cli.devCard.deckTitle",
                cardDeck.getNumberOfCardsInDeck()
            )
        ));

        //check if the card on top of the deck has changed
        if(!cardDeck.cardOnTop.equals(card))
            createNewDevCardAndDeckLabel(); //Draw new card

        return super.getContentAsFormattedCharsBuffer();
    }

    protected void createNewDevCardAndDeckLabel() {

        //Remove and destroy old card if present
        if (cardView != null) {
            cardView.destroyView();
        }

        children.clear();

        addChildView(deckLabel, rowSize-1, 0);
        deckLabel.setRowSize(1);
        deckLabel.setColumnSize(columnSize);

        if (cardDeck.getNumberOfCardsInDeck() > 0) {
            card = cardDeck.getCardOnTop();
            cardView = new DevCardView(card, clientManager);
            addChildView(cardView, 0, 0);
        } else {
            card = null;
            //TODO
        }

    }

    public void setCardBorderColour(Colour colour) {
        cardView.setCardBorderColour(colour);
    }

    public ClientDevelopmentCardRepresentation getCardOnTop() {
        return card;
    }

}
