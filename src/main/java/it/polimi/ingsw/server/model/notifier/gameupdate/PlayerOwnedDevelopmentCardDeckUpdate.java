package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.utils.serialization.annotations.*;

import java.util.List;

public class PlayerOwnedDevelopmentCardDeckUpdate extends GameUpdate {

    @SerializeIdOnly
    public final PlayerOwnedDevelopmentCardDeck deck;

	public final List<DevelopmentCard> developmentCardsDeck;

    public PlayerOwnedDevelopmentCardDeckUpdate(
        PlayerOwnedDevelopmentCardDeck deck,
        List<DevelopmentCard> developmentCardsDeck
    ) {
        this.deck = deck;
        this.developmentCardsDeck = developmentCardsDeck;
    }

}
