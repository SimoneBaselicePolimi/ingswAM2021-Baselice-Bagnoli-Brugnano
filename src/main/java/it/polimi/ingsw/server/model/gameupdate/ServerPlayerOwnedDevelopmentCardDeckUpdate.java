package it.polimi.ingsw.server.model.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ServerPlayerOwnedDevelopmentCardDeckUpdate extends ServerGameUpdate {

    @SerializeIdOnly
    public final PlayerOwnedDevelopmentCardDeck deck;

    @SerializeIdOnly
	public final DevelopmentCard developmentCardAddedToDeck;

    public ServerPlayerOwnedDevelopmentCardDeckUpdate(
        @JsonProperty("deck") PlayerOwnedDevelopmentCardDeck deck,
        @JsonProperty("developmentCardAddedToDeck") DevelopmentCard developmentCardAddedToDeck
    ) {
        this.deck = deck;
        this.developmentCardAddedToDeck = developmentCardAddedToDeck;
    }

}
