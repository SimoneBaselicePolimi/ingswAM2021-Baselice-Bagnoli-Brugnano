package it.polimi.ingsw.server.model.notifier.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.utils.serialization.annotations.*;

import java.util.List;

public class ServerPlayerOwnedDevelopmentCardDeckUpdate extends ServerGameUpdate {

    @SerializeIdOnly
    public final PlayerOwnedDevelopmentCardDeck deck;

	public final List<DevelopmentCard> developmentCardsDeck;

    public ServerPlayerOwnedDevelopmentCardDeckUpdate(
        @JsonProperty("deck") PlayerOwnedDevelopmentCardDeck deck,
        @JsonProperty("developmentCardsDeck") List<DevelopmentCard> developmentCardsDeck
    ) {
        this.deck = deck;
        this.developmentCardsDeck = developmentCardsDeck;
    }

}
