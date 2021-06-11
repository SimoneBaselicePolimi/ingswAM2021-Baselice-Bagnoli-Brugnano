package it.polimi.ingsw.gameactionshistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class DiscardedResourcesMarketAction extends GameAction {

    @SerializeIdOnly
    private final Player player;

    private final int numberOfResourcesDiscarded;

    public DiscardedResourcesMarketAction(
        @JsonProperty("player") Player player,
        @JsonProperty("numberOfResourcesDiscarded") int numberOfResourcesDiscarded
    ) {
        this.player = player;
        this.numberOfResourcesDiscarded = numberOfResourcesDiscarded;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        if (numberOfResourcesDiscarded == 1)
            return Localization.getLocalizationInstance().getString(
                "gameHistory.gameState.manageResourcesFromMarketTurn. discardedResourcesMarketAction.singular",
                player
            );
        else
            return Localization.getLocalizationInstance().getString(
                "gameHistory.gameState.manageResourcesFromMarketTurn. discardedResourcesMarketAction.plural",
                player,
                numberOfResourcesDiscarded
            );
    }

}
