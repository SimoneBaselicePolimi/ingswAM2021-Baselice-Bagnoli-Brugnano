package it.polimi.ingsw.client.modelrepresentation;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientRegisteredIdentifiableItemRepresentation;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;

public class ClientPlayerRepresentation extends ClientRegisteredIdentifiableItemRepresentation {

    protected ClientPlayerRepresentation(
        @JsonProperty("itemID") String itemID,
        @JacksonInject("gameItemsManager") GameItemsManager gameItemsManager
    ) {
        super(itemID, gameItemsManager);
    }

}
