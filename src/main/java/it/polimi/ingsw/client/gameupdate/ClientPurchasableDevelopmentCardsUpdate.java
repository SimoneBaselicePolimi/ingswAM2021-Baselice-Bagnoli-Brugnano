package it.polimi.ingsw.client.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.PurchasableDevelopmentCardsUpdateHandler;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;

import java.util.Set;

public class ClientPurchasableDevelopmentCardsUpdate extends ClientGameUpdate{

    @SerializeAsSetOfIds
    public final Set<ClientDevelopmentCardRepresentation> purchasableDevelopmentCards;

    public ClientPurchasableDevelopmentCardsUpdate(
        @JsonProperty("purchasableDevelopmentCards") Set<ClientDevelopmentCardRepresentation> purchasableDevelopmentCards
    ) {
        this.purchasableDevelopmentCards = purchasableDevelopmentCards;
    }

    @Override
    public GameUpdateHandler getHandler() {
        return new PurchasableDevelopmentCardsUpdateHandler();
    }
}
