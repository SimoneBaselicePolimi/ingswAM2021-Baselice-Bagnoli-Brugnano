package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ServerCoveredCardsDeckRepresentation<C extends ServerRepresentation> extends ServerCardDeckRepresentation<C> {

    @SerializeIdOnly
    public final C cardOnTop;  //Only the card on top of the deck should be visible to the player

    public final int numberOfCardsInDeck;

    public ServerCoveredCardsDeckRepresentation(
        @JsonProperty("itemID") String itemID,
        @JsonProperty("cardOnTop") C cardOnTop,
        @JsonProperty("numberOfCardsInDeck") int numberOfCardsInDeck
    ) {
        super(itemID);
        this.cardOnTop = cardOnTop;
        this.numberOfCardsInDeck = numberOfCardsInDeck;
    }
}
