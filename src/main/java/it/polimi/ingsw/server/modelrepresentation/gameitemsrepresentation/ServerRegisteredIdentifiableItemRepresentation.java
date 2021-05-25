package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;

public class ServerRegisteredIdentifiableItemRepresentation extends ServerRepresentation implements IdentifiableItem {

    public final String itemID;

    public ServerRegisteredIdentifiableItemRepresentation(
        @JsonProperty("itemID") String itemID
    ) {
        this.itemID = itemID;
    }

    @Override
    public String getItemID() {
        return itemID;
    }

}
