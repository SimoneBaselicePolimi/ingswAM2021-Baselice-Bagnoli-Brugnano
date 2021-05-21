package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;

import java.util.Stack;

public class ClientPlayerOwnedDevelopmentCardDeckRepresentation extends ClientCardDeckRepresentation<ClientDevelopmentCardRepresentation> {

    /**
     * CardDeckRepresentation constructor.
     *
     * @param itemID           ID which identifies this specific Card Deck
     * @param gameItemsManager a reference to gameItemsManager is needed to register the new CardDeck object
     *                         (see {@link RegisteredIdentifiableItem})
     * @param cardDeck
     */
    protected ClientPlayerOwnedDevelopmentCardDeckRepresentation(
        @JsonProperty("itemID") String itemID,
        @JacksonInject("gameItemsManager") GameItemsManager gameItemsManager,
        @JsonProperty("cardDeck") Stack<ClientDevelopmentCardRepresentation> cardDeck
    ) {
        super(itemID, gameItemsManager, cardDeck);
    }
}
