package it.polimi.ingsw.client.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.PurchasableDevelopmentCardsUpdateHandler;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Set;

public class ClientPurchasableDevelopmentCardsUpdate extends ClientGameUpdate{

    @SerializeIdOnly
    public final Player player;

    @SerializeAsSetOfIds
    public final Set<ClientDevelopmentCardRepresentation> purchasableDevelopmentCards;

    public ClientPurchasableDevelopmentCardsUpdate(
        @JsonProperty("player") Player player,
        @JsonProperty("purchasableDevelopmentCards") Set<ClientDevelopmentCardRepresentation> purchasableDevelopmentCards
    ) {
        this.player = player;
        this.purchasableDevelopmentCards = purchasableDevelopmentCards;
    }

    @Override
    public GameUpdateHandler getHandler() {
        return new PurchasableDevelopmentCardsUpdateHandler();
    }
}
