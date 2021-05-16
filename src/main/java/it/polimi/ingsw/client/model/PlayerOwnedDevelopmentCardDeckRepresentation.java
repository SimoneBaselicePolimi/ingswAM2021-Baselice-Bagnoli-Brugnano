package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;

public class PlayerOwnedDevelopmentCardDeckRepresentation extends CardDeckRepresentation<DevelopmentCardRepresentation>{
    /**
     * Class constructor.
     * @param itemID ID which identifies this specific Card Deck
     * @param gameItemsManager a reference to gameItemsManager is needed to register the new PlayerOwnedDevelopmentCardDeck object
     * (see {@link RegisteredIdentifiableItem})
     */
    protected PlayerOwnedDevelopmentCardDeckRepresentation(
        String itemID,
        GameItemsManager gameItemsManager
    ) {
        super(itemID, gameItemsManager);
    }
}
