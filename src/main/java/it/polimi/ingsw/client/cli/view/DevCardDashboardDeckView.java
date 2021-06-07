package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientPlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.server.model.Player;

import java.util.ArrayList;

public class DevCardDashboardDeckView extends CliView {

    public static final int DEV_CARD_DECK_ROW_SIZE = DevCardView.DEV_CARD_ROW_SIZE+1;
    public static final int DEV_CARD_DECK_COL_SIZE = DevCardView.DEV_CARD_COL_SIZE;

    public final int deckNum;
    public final Player player;

    protected final ClientPlayerOwnedDevelopmentCardDeckRepresentation cardDeck;

    LabelView deckLabel;

    DevCardView cardView;

    ClientDevelopmentCardRepresentation card;

    public DevCardDashboardDeckView(
        Player player,
        int deckNumber,
        CliClientManager clientManager
    ) {
        super(clientManager, DEV_CARD_DECK_ROW_SIZE, DEV_CARD_DECK_COL_SIZE);
        this.deckNum = deckNumber;
        this.player = player;
        this.cardDeck = clientManager.getGameContextRepresentation()
            .getPlayerContext(player).getDevelopmentCardDecks().get(deckNumber);
        deckLabel = new LabelView(new ArrayList<>(), clientManager);

        createNewDevCardAndDeckLabel();
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {

        deckLabel.setText(FormattedChar.convertStringToFormattedCharList(getCoveredCardsDescription()));

        //Check if the card on top of the deck has changed
        if(!cardDeck.getCardDeck().empty() && !cardDeck.peek().equals(card))
            createNewDevCardAndDeckLabel(); //Draw new card

        return super.getContentAsFormattedCharsBuffer();
    }

    private String getCoveredCardsDescription() {
        StringBuilder description = new StringBuilder();
        if(!cardDeck.getCardDeck().empty()) {
            description.append(deckNum+1).append(": ");
            for (ClientDevelopmentCardRepresentation card : cardDeck.getCardDeck()) {
                if (!card.equals(cardDeck.peek())) {
                    description.append("[")
                        .append(card.getColour().getColourNameLocalizedSingular())
                        .append(", ").append(card.getVictoryPoints()).append("] ");
                }
            }
        }
        return description.toString();
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

        if (cardDeck.getCardDeck().size() > 0) {
            card = cardDeck.peek();
            cardView = new DevCardView(card, clientManager);
            addChildView(cardView, 0, 0);
        } else {
            card = null;
            //TODO
        }

    }

}
