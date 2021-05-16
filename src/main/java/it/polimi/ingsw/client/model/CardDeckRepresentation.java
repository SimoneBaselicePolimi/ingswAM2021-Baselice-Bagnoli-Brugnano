package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;

import java.util.Stack;

public class CardDeckRepresentation <C> extends RegisteredIdentifiableItemRepresentation{

    /**
     * Stack of generics which represents the Card Deck
     */
    protected Stack<C> cardDeck = new Stack<>();

    /**
     * CardDeckRepresentation constructor.
     * @param itemID ID which identifies this specific Card Deck
     * @param gameItemsManager a reference to gameItemsManager is needed to register the new CardDeck object
     * (see {@link RegisteredIdentifiableItem})
     */
    protected CardDeckRepresentation(String itemID, GameItemsManager gameItemsManager, Stack<C> cardDeck) {
        super(itemID, gameItemsManager);
        this.cardDeck = cardDeck;
    }

    public Stack<C> getCardDeck() {
        return cardDeck;
    }

    public void setCardDeck(Stack<C> cardDeck) {
        this.cardDeck = cardDeck;
    }
}
