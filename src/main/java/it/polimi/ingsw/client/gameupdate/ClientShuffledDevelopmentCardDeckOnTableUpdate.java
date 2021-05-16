package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.ShuffledDevelopmentCardDeckOnTableUpdateHandler;
import it.polimi.ingsw.client.model.CardDeckRepresentation;
import it.polimi.ingsw.client.model.DevelopmentCardRepresentation;
import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientShuffledDevelopmentCardDeckOnTableUpdate extends ClientGameUpdate {

    @SerializeIdOnly
    public final CardDeckRepresentation<DevelopmentCardRepresentation> deck;

    @SerializeIdOnly
	public final DevelopmentCardRepresentation cardOnTop;  //Only the card on top of the deck should be visible to the player

    public final int numberOfCardsInDeck;

    public ClientShuffledDevelopmentCardDeckOnTableUpdate(
        CardDeckRepresentation<DevelopmentCardRepresentation> deck,
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
