package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientShuffledDevelopmentCardDeckOnTableUpdate extends ClientGameUpdate {

    @SerializeIdOnly
    public final ShuffledCardDeck<DevelopmentCard> deck;

    @SerializeIdOnly
	public final DevelopmentCard cardOnTop;  //Only the card on top of the deck should be visible to the player

    public final int numberOfCardsInDeck;

    public ClientShuffledDevelopmentCardDeckOnTableUpdate(
        ShuffledCardDeck<DevelopmentCard> deck,
        DevelopmentCard cardOnTop,
        int numberOfCardsInDeck
    ) {
        this.deck = deck;
        this.cardOnTop = cardOnTop;
        this.numberOfCardsInDeck = numberOfCardsInDeck;
    }

}
