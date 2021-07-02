package it.polimi.ingsw.server.model.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Set;

public class ServerPurchasableDevelopmentCardsUpdate extends ServerGameUpdate {

    @SerializeIdOnly
    public final Player player;

    @SerializeAsSetOfIds
    public final Set<DevelopmentCard> purchasableDevelopmentCards;

    public ServerPurchasableDevelopmentCardsUpdate(
        @JsonProperty("player") Player player,
        @JsonProperty("purchasableDevelopmentCards") Set<DevelopmentCard> purchasableDevelopmentCards
    ) {
        this.player = player;
        this.purchasableDevelopmentCards = purchasableDevelopmentCards;
    }
}
