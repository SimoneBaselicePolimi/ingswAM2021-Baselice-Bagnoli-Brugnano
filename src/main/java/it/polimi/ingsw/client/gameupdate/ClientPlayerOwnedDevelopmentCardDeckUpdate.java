package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.List;

public class ClientPlayerOwnedDevelopmentCardDeckUpdate extends ClientGameUpdate {

    @SerializeIdOnly
    public final PlayerOwnedDevelopmentCardDeck deck;

	public final List<DevelopmentCard> developmentCardsDeck;

    public ClientPlayerOwnedDevelopmentCardDeckUpdate(
        PlayerOwnedDevelopmentCardDeck deck,
        List<DevelopmentCard> developmentCardsDeck
    ) {
        this.deck = deck;
        this.developmentCardsDeck = developmentCardsDeck;
    }

}
