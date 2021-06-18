package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;

public class ClientRegisteredIdentifiableItemRepresentation extends ClientRepresentation implements IdentifiableItem {

    protected final String itemID;

    protected ClientRegisteredIdentifiableItemRepresentation(
        @JsonProperty("itemID") String itemID,
        @JacksonInject("gameItemsManager") GameItemsManager gameItemsManager
    ) {
        this.itemID = itemID;
        gameItemsManager.addItem(this);
    }

    @Override
    public String getItemID() {
        return itemID;
    }

}
