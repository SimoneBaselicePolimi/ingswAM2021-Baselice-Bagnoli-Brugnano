package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;

public class ClientRegisteredIdentifiableItemRepresentation extends ClientRepresentation implements IdentifiableItem {
    protected final String itemID;

    protected ClientRegisteredIdentifiableItemRepresentation(String itemID, GameItemsManager gameItemsManager) {
        this.itemID = itemID;
        gameItemsManager.addItem(this);
    }

    @Override
    public String getItemId() {
        return itemID;
    }

}
