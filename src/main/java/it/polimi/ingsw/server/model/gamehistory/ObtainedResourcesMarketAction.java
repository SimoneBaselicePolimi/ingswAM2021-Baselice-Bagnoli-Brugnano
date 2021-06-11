package it.polimi.ingsw.server.model.gamehistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class ObtainedResourcesMarketAction extends GameAction {
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
