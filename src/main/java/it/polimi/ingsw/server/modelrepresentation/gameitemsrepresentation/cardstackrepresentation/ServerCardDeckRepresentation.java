package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerRegisteredIdentifiableItemRepresentation;

import java.util.Stack;

public class ServerCardDeckRepresentation<C> extends ServerRegisteredIdentifiableItemRepresentation {
    protected ServerCardDeckRepresentation(String itemID, GameItemsManager gameItemsManager) {
        super(itemID, gameItemsManager);
    }

//    /**
//     * Stack of generics which represents the Card Deck
//     */
//    public final Stack<C> cardDeck = new Stack<>();
//
//    /**
//     * CardDeckRepresentation constructor.
//     * @param itemID ID which identifies this specific Card Deck
//     * @param gameItemsManager a reference to gameItemsManager is needed to register the new CardDeck object
//     * (see {@link RegisteredIdentifiableItem})
//     */
//    protected ServerCardDeckRepresentation(String itemID, GameItemsManager gameItemsManager, Stack<C> cardDeck) {
//        super(itemID, gameItemsManager);
//        this.cardDeck = cardDeck;
//    }
}
