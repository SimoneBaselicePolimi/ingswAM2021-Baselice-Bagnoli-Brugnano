package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class CoveredCardsDeckRepresentation<C extends Representation> extends RegisteredIdentifiableItemRepresentation{

    @SerializeIdOnly
    public DevelopmentCardRepresentation cardOnTop;  //Only the card on top of the deck should be visible to the player

    public int numberOfCardsInDeck;

    public CoveredCardsDeckRepresentation(String itemID, GameItemsManager gameItemsManager, DevelopmentCardRepresentation cardOnTop, int numberOfCardsInDeck) {
        super(itemID, gameItemsManager);
        this.cardOnTop = cardOnTop;
        this.numberOfCardsInDeck = numberOfCardsInDeck;
    }

    public DevelopmentCardRepresentation getCardOnTop() {
        return cardOnTop;
    }

    public int getNumberOfCardsInDeck() {
        return numberOfCardsInDeck;
    }

    public void setCardOnTop(DevelopmentCardRepresentation cardOnTop) {
        this.cardOnTop = cardOnTop;
    }

    public void setNumberOfCardsInDeck(int numberOfCardsInDeck) {
        this.numberOfCardsInDeck = numberOfCardsInDeck;
    }
}
