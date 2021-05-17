package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;

import java.util.Stack;

public class PlayerOwnedDevelopmentCardDeckRepresentation extends CardDeckRepresentation<DevelopmentCardRepresentation>{

    /**
     * CardDeckRepresentation constructor.
     *
     * @param itemID           ID which identifies this specific Card Deck
     * @param gameItemsManager a reference to gameItemsManager is needed to register the new CardDeck object
     *                         (see {@link RegisteredIdentifiableItem})
     * @param cardDeck
     */
    protected PlayerOwnedDevelopmentCardDeckRepresentation(String itemID, GameItemsManager gameItemsManager, Stack<DevelopmentCardRepresentation> cardDeck) {
        super(itemID, gameItemsManager, cardDeck);
    }
}
