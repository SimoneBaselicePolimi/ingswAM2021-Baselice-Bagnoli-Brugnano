package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.PlayerOwnedDevelopmentCardDeckUpdateHandler;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientPlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Stack;

public class ClientPlayerOwnedDevelopmentCardDeckUpdate extends ClientGameUpdate {

    @SerializeIdOnly
    public final ClientPlayerOwnedDevelopmentCardDeckRepresentation deck;

	public final Stack<ClientDevelopmentCardRepresentation> developmentCardsDeck;

    public ClientPlayerOwnedDevelopmentCardDeckUpdate(
        ClientPlayerOwnedDevelopmentCardDeckRepresentation deck,
        Stack<ClientDevelopmentCardRepresentation> developmentCardsDeck
    ) {
        this.deck = deck;
        this.developmentCardsDeck = developmentCardsDeck;
    }

    @Override
    public GameUpdateHandler getHandler() {
        return new PlayerOwnedDevelopmentCardDeckUpdateHandler();
    }
}
