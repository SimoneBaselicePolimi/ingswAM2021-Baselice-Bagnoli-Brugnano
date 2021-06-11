package it.polimi.ingsw.gameactionshistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Map;

public class ObtainedResourcesMarketAction extends GameAction {

    @SerializeIdOnly
    private final Player player;

    private final Map<ResourceType, Integer> resourcesObtained;

    public ObtainedResourcesMarketAction(
        @JsonProperty("player") Player player,
        @JsonProperty("resourcesObtained") Map<ResourceType, Integer> resourcesObtained
    ) {
        this.player = player;
        this.resourcesObtained = resourcesObtained;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.gameState.manageResourcesFromMarketTurn.initialMessage",
            player.getName(),
            LocalizationUtils.getResourcesListAsString(resourcesObtained)
        );
    }

}
