package it.polimi.ingsw.server.model.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCoveredCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;
import it.polimi.ingsw.utils.serialization.annotations.*;

import java.util.Objects;

public class ServerShuffledDevelopmentCardDeckOnTableUpdate extends ServerGameUpdate {

    @SerializeIdOnly
    public final ShuffledCoveredCardDeck<ServerDevelopmentCardRepresentation, DevelopmentCard> deck;

    @SerializeIdOnly
	public final DevelopmentCard cardOnTop;  //Only the card on top of the deck should be visible to the player

    public final int numberOfCardsInDeck;

    public ServerShuffledDevelopmentCardDeckOnTableUpdate(
        @JsonProperty("deck") ShuffledCoveredCardDeck<ServerDevelopmentCardRepresentation, DevelopmentCard> deck,
        @JsonProperty("cardOnTop") DevelopmentCard cardOnTop,
        @JsonProperty("numberOfCardsInDeck") int numberOfCardsInDeck
    ) {
        this.deck = deck;
        this.cardOnTop = cardOnTop;
        this.numberOfCardsInDeck = numberOfCardsInDeck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerShuffledDevelopmentCardDeckOnTableUpdate that = (ServerShuffledDevelopmentCardDeckOnTableUpdate) o;
        return deck.equals(that.deck);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deck);
    }

}
