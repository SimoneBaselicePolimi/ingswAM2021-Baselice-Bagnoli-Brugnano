package it.polimi.ingsw.server.model.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;

import java.util.Set;

public class ServerPurchasableDevelopmentCardsUpdate extends ServerGameUpdate {

    @SerializeAsSetOfIds
    public final Set<ServerDevelopmentCardRepresentation> purchasableDevelopmentCards;

    public ServerPurchasableDevelopmentCardsUpdate(
        @JsonProperty("purchasableDevelopmentCards") Set<ServerDevelopmentCardRepresentation> purchasableDevelopmentCards
    ) {
        this.purchasableDevelopmentCards = purchasableDevelopmentCards;
    }
}
