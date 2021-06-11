package it.polimi.ingsw.gameactionshistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.HashMap;
import java.util.Map;

public class SetupChoiceAction extends GameAction{

    @SerializeIdOnly
    private final Player player;

    private final Map<ResourceType,Integer> initialResources;

    public SetupChoiceAction(
        @JsonProperty("player") Player player,
        @JsonProperty("initialResources") Map<ResourceType, Integer> initialResources
    ) {
        this.player = player;
        this.initialResources = initialResources == null ? new HashMap<>() : initialResources;
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
