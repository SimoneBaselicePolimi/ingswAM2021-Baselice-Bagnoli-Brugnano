package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.PlayerOwnedDevelopmentCardDeckUpdateHandler;
import it.polimi.ingsw.client.model.DevelopmentCardRepresentation;
import it.polimi.ingsw.client.model.PlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.List;

public class ClientPlayerOwnedDevelopmentCardDeckUpdate extends ClientGameUpdate {

    @SerializeIdOnly
    public final PlayerOwnedDevelopmentCardDeckRepresentation deck;

	public final List<DevelopmentCardRepresentation> developmentCardsDeck;

    public ClientPlayerOwnedDevelopmentCardDeckUpdate(
        PlayerOwnedDevelopmentCardDeckRepresentation deck,
        List<DevelopmentCardRepresentation> developmentCardsDeck
    ) {
        this.deck = deck;
        this.developmentCardsDeck = developmentCardsDeck;
    }

    @Override
    public GameUpdateHandler getHandler() {
        return new PlayerOwnedDevelopmentCardDeckUpdateHandler();
    }
}
