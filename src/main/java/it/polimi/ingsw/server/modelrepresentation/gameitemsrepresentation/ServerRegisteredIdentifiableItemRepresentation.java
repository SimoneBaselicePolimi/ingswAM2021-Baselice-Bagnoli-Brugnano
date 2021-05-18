package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation;

import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;

public class ServerRegisteredIdentifiableItemRepresentation extends ServerRepresentation implements IdentifiableItem {
    public final String itemID;

    public ServerRegisteredIdentifiableItemRepresentation(String itemID) {
        this.itemID = itemID;
    }

    @Override
    public String getItemId() {
        return itemID;
    }

}
