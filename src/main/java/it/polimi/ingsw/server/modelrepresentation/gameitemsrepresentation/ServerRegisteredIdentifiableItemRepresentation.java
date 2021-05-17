package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;

public class ServerRegisteredIdentifiableItemRepresentation extends ClientRepresentation implements IdentifiableItem {
    protected final String itemID;

    protected ServerRegisteredIdentifiableItemRepresentation(String itemID, GameItemsManager gameItemsManager) {
        this.itemID = itemID;
        gameItemsManager.addItem(this);
    }

    @Override
    public String getItemId() {
        return itemID;
    }

}
