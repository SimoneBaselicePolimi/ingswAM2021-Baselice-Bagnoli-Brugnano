package it.polimi.ingsw.server.model.gamehistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class SetupChoiceAction extends GameAction{
    private final Player player;
    private final Map<ResourceType,Integer> initialResources;

    public SetupChoiceAction(
        @JsonProperty("player") Player player,
        @JsonProperty("initialResources") Map<ResourceType, Integer> initialResources
    ) {
        this.player = player;
        this.initialResources = initialResources;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.gameState.setup.initialMessage",
            player.getName(),
            LocalizationUtils.getResourcesListAsString(initialResources)
        );
    }

}
