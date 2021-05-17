package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;

import java.util.Stack;

public class ServerPlayerOwnedDevelopmentCardDeckRepresentation extends ServerCardDeckRepresentation<ServerDevelopmentCardRepresentation> {

    /**
     * CardDeckRepresentation constructor.
     *
     * @param itemID           ID which identifies this specific Card Deck
     * @param gameItemsManager a reference to gameItemsManager is needed to register the new CardDeck object
     *                         (see {@link RegisteredIdentifiableItem})
     * @param cardDeck
     */
    protected ServerPlayerOwnedDevelopmentCardDeckRepresentation(
        String itemID,
        GameItemsManager gameItemsManager,
        Stack<ServerDevelopmentCardRepresentation> cardDeck
    ) {
        super(itemID, gameItemsManager, cardDeck);
    }
}
