package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;

public class PlayerRepresentation extends RegisteredIdentifiableItemRepresentation{

    protected PlayerRepresentation(String itemID, GameItemsManager gameItemsManager) {
        super(itemID, gameItemsManager);
    }

}
