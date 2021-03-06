package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerRegisteredIdentifiableItemRepresentation;

import java.util.Stack;

public class ServerCardDeckRepresentation<C> extends ServerRegisteredIdentifiableItemRepresentation {

    /**
    * Stack of generics which represents the Card Deck
     */
    public final Stack<C> cardDeck = new Stack<>();

    public ServerCardDeckRepresentation(
        @JsonProperty("itemID") String itemID
    ) {
        super(itemID);
    }
}
