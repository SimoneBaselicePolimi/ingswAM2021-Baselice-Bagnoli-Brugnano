package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;

public class RegisteredIdentifiableItemRepresentation extends Representation implements IdentifiableItem {
    protected final String itemID;

    protected RegisteredIdentifiableItemRepresentation(String itemID, GameItemsManager gameItemsManager) {
        this.itemID = itemID;
        gameItemsManager.addItem(this);
    }

    @Override
    public String getItemId() {
        return itemID;
    }

}
