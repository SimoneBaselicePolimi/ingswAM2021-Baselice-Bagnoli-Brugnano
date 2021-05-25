package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientRegisteredIdentifiableItemRepresentation;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientCoveredCardsDeckRepresentation<C extends ClientRepresentation & IdentifiableItem> extends ClientRegisteredIdentifiableItemRepresentation {

    @SerializeIdOnly
    public C cardOnTop;  //Only the card on top of the deck should be visible to the player

    public int numberOfCardsInDeck;

    public ClientCoveredCardsDeckRepresentation(
        @JsonProperty("itemID") String itemID,
        @JacksonInject("gameItemsManager") GameItemsManager gameItemsManager,
        @JsonProperty("cardOnTop") C cardOnTop,
        @JsonProperty("numberOfCardsInDeck") int numberOfCardsInDeck
    ) {
        super(itemID, gameItemsManager);
        this.cardOnTop = cardOnTop;
        this.numberOfCardsInDeck = numberOfCardsInDeck;
    }

    public C getCardOnTop() {
        return cardOnTop;
    }

    public int getNumberOfCardsInDeck() {
        return numberOfCardsInDeck;
    }

    public void setCardOnTop(C cardOnTop) {
        this.cardOnTop = cardOnTop;
    }

    public void setNumberOfCardsInDeck(int numberOfCardsInDeck) {
        this.numberOfCardsInDeck = numberOfCardsInDeck;
    }
}
