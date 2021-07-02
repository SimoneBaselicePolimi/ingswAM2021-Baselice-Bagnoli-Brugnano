package it.polimi.ingsw.client.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.PlayerOwnedDevelopmentCardDeckUpdateHandler;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientPlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientPlayerOwnedDevelopmentCardDeckUpdate extends ClientGameUpdate {

    @SerializeIdOnly
    public final ClientPlayerOwnedDevelopmentCardDeckRepresentation deck;

    @SerializeIdOnly
	public final ClientDevelopmentCardRepresentation developmentCardAddedToDeck;

    public ClientPlayerOwnedDevelopmentCardDeckUpdate(
        @JsonProperty("deck") ClientPlayerOwnedDevelopmentCardDeckRepresentation deck,
        @JsonProperty("developmentCardAddedToDeck") ClientDevelopmentCardRepresentation developmentCardAddedToDeck
    ) {
        this.deck = deck;
        this.developmentCardAddedToDeck = developmentCardAddedToDeck;
    }

    @Override
    public GameUpdateHandler getHandler() {
        return new PlayerOwnedDevelopmentCardDeckUpdateHandler();
    }
}
