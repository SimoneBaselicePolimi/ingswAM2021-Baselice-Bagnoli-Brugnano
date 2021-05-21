package it.polimi.ingsw.server.modelrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerRegisteredIdentifiableItemRepresentation;

public class ServerPlayerRepresentation extends ServerRegisteredIdentifiableItemRepresentation {

    public ServerPlayerRepresentation(
        @JsonProperty("itemID") String itemID
    ) {
        super(itemID);
    }

}
