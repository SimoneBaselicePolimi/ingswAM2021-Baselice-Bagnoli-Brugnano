package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientRegisteredIdentifiableItemRepresentation;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientCoveredCardsDeckRepresentation<C extends ClientRepresentation> extends ClientRegisteredIdentifiableItemRepresentation {

    @SerializeIdOnly
    public ClientDevelopmentCardRepresentation cardOnTop;  //Only the card on top of the deck should be visible to the player

    public int numberOfCardsInDeck;

    public ClientCoveredCardsDeckRepresentation(String itemID, GameItemsManager gameItemsManager, ClientDevelopmentCardRepresentation cardOnTop, int numberOfCardsInDeck) {
        super(itemID, gameItemsManager);
        this.cardOnTop = cardOnTop;
        this.numberOfCardsInDeck = numberOfCardsInDeck;
    }

    public ClientDevelopmentCardRepresentation getCardOnTop() {
        return cardOnTop;
    }

    public int getNumberOfCardsInDeck() {
        return numberOfCardsInDeck;
    }

    public void setCardOnTop(ClientDevelopmentCardRepresentation cardOnTop) {
        this.cardOnTop = cardOnTop;
    }

    public void setNumberOfCardsInDeck(int numberOfCardsInDeck) {
        this.numberOfCardsInDeck = numberOfCardsInDeck;
    }
}
