package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.ShuffledDevelopmentCardDeckOnTableUpdateHandler;
import it.polimi.ingsw.client.model.CoveredCardsDeckRepresentation;
import it.polimi.ingsw.client.model.DevelopmentCardRepresentation;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientShuffledDevelopmentCardDeckOnTableUpdate extends ClientGameUpdate {

    @SerializeIdOnly
    public final CoveredCardsDeckRepresentation<DevelopmentCardRepresentation> deck;

    @SerializeIdOnly
	public final DevelopmentCardRepresentation cardOnTop;  //Only the card on top of the deck should be visible to the player

    public final int numberOfCardsInDeck;

    public ClientShuffledDevelopmentCardDeckOnTableUpdate(
        CoveredCardsDeckRepresentation<DevelopmentCardRepresentation> deck,
        DevelopmentCardRepresentation cardOnTop,
        int numberOfCardsInDeck
    ) {
        this.deck = deck;
        this.cardOnTop = cardOnTop;
        this.numberOfCardsInDeck = numberOfCardsInDeck;
    }

    @Override
    public GameUpdateHandler getHandler() {
        return new ShuffledDevelopmentCardDeckOnTableUpdateHandler();
    }
}
