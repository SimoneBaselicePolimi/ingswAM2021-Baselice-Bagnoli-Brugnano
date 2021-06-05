package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCoveredCardsDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.ArrayList;

public class DevCardTableDeckView extends CliView {

    public final DevelopmentCardColour cardColour;
    public final DevelopmentCardLevel cardLevel;

    protected final ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation> cardDeck;

    LabelView deckLabel;

    DevelopmentCardView cardView;

    ClientDevelopmentCardRepresentation card;

    public DevCardTableDeckView(
        DevelopmentCardColour cardColour,
        DevelopmentCardLevel cardLevel,
        CliClientManager clientManager,
        int rowSize,
        int columnSize
    ) {
        super(clientManager, rowSize, columnSize);
        this.cardColour = cardColour;
        this.cardLevel = cardLevel;
        this.cardDeck = clientManager.getGameContextRepresentation()
            .getDevelopmentCardsTable().getDeck(cardLevel, cardColour);

        deckLabel = new LabelView(new ArrayList<>(), clientManager);

    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {

        deckLabel.setText(FormattedChar.convertStringToFormattedCharList(
            Localization.getLocalizationInstance().getString(
                "client.cli.devCard.deckTitle",
                cardDeck.getNumberOfCardsInDeck()
            )
        ));

        //check if teh card on top of the deck has changed
        if(!cardDeck.cardOnTop.equals(card)) {

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
                cardView = new DevelopmentCardView(card, clientManager, rowSize-1, columnSize);
                addChildView(cardView, 0, 0);
            } else {
                //TODO
            }

        }
        return super.getContentAsFormattedCharsBuffer();
    }

    public DevCardTableDeckView(DevelopmentCardColour cardColour, DevelopmentCardLevel cardLevel, CliClientManager clientManager) {
        this(cardColour, cardLevel, clientManager, 0, 0);
    }

}
