package it.polimi.ingsw.server.gameactionshistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class DiscardedResourcesMarketAction extends GameAction {

    @SerializeIdOnly
    private final Player player;

    @JsonProperty("numberOfResourcesDiscarded")
    private final int numberOfResourcesDiscarded;

    public DiscardedResourcesMarketAction(
        @JsonProperty("player") Player player,
        @JsonProperty("numberOfResourcesDiscarded") int numberOfResourcesDiscarded
    ) {
        this.player = player;
        this.numberOfResourcesDiscarded = numberOfResourcesDiscarded;
    }

}
