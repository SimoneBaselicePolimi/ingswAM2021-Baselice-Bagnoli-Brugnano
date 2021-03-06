package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientRegisteredIdentifiableItemRepresentation;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;

import java.util.EmptyStackException;
import java.util.Stack;

public class ClientCardDeckRepresentation<C> extends ClientRegisteredIdentifiableItemRepresentation {

    /**
     * Stack of generics which represents the Card Deck
     */
    protected Stack<C> cardDeck;

    /**
     * CardDeckRepresentation constructor.
     * @param itemID ID which identifies this specific Card Deck
     * @param gameItemsManager a reference to gameItemsManager is needed to register the new CardDeck object
     * (see {@link RegisteredIdentifiableItem})
     */
    protected ClientCardDeckRepresentation(
        @JsonProperty("itemID") String itemID,
        @JacksonInject("gameItemsManager") GameItemsManager gameItemsManager,
        @JsonProperty("cardDeck") Stack<C> cardDeck
    ) {
        super(itemID, gameItemsManager);
        this.cardDeck = cardDeck;
    }

    public Stack<C> getCardDeck() {
        return cardDeck;
    }

    public void setCardDeck(Stack<C> cardDeck) {
        this.cardDeck = cardDeck;
        notifyViews();
    }

    public C peek() throws EmptyStackException {
        return cardDeck.peek();
    }

    public int numberOfCardsInDeck() {
        return cardDeck.size();
    }

}
