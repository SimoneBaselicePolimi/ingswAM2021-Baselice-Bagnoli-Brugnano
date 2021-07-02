package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;

public class ServerPlayerOwnedDevelopmentCardDeckRepresentation extends ServerCardDeckRepresentation<ServerDevelopmentCardRepresentation> {

    public ServerPlayerOwnedDevelopmentCardDeckRepresentation(
        @JsonProperty("itemID") String itemID
    ) {
        super(itemID);
    }

}
