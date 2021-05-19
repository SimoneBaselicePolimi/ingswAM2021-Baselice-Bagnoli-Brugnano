package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.ShuffledDevelopmentCardDeckOnTableUpdateHandler;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCoveredCardsDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientShuffledDevelopmentCardDeckOnTableUpdate extends ClientGameUpdate {

    @SerializeIdOnly
    public final ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation> deck;

    @SerializeIdOnly
	public final ClientDevelopmentCardRepresentation cardOnTop;  //Only the card on top of the deck should be visible to the player

    public final int numberOfCardsInDeck;

    public ClientShuffledDevelopmentCardDeckOnTableUpdate(
        ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation> deck,
        ClientDevelopmentCardRepresentation cardOnTop,
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
