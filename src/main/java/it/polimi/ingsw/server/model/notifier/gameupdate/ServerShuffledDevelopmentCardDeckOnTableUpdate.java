package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCoveredCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;
import it.polimi.ingsw.utils.serialization.annotations.*;

public class ServerShuffledDevelopmentCardDeckOnTableUpdate extends ServerGameUpdate {

    @SerializeIdOnly
    public final ShuffledCoveredCardDeck<ServerDevelopmentCardRepresentation, DevelopmentCard> deck;

    @SerializeIdOnly
	public final DevelopmentCard cardOnTop;  //Only the card on top of the deck should be visible to the player

    public final int numberOfCardsInDeck;

    public ServerShuffledDevelopmentCardDeckOnTableUpdate(
        ShuffledCoveredCardDeck<ServerDevelopmentCardRepresentation, DevelopmentCard> deck,
        DevelopmentCard cardOnTop,
        int numberOfCardsInDeck
    ) {
        this.deck = deck;
        this.cardOnTop = cardOnTop;
        this.numberOfCardsInDeck = numberOfCardsInDeck;
    }

}
