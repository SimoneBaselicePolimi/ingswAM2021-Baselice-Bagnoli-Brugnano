package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Map;

public class ClientObtainedResourcesMarketActionRepresentation extends ClientGameActionRepresentation {

    @SerializeIdOnly
    public final Player player;

    public final Map<ResourceType, Integer> resourcesObtained;

    public ClientObtainedResourcesMarketActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("resourcesObtained") Map<ResourceType, Integer> resourcesObtained
    ) {
        this.player = player;
        this.resourcesObtained = resourcesObtained;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        if(!resourcesObtained.isEmpty())
            return Localization.getLocalizationInstance().getString(
                "gameHistory.gameState.manageResourcesFromMarketTurn.obtainedResourcesMarketMessage",
                player.getName(),
                LocalizationUtils.getResourcesListAsCompactString(resourcesObtained)
            );
        else
            return Localization.getLocalizationInstance().getString(
                "gameHistory.gameState.manageResourcesFromMarketTurn.noObtainedResourcesMarketMessage",
                player.getName()
            );
    }

}
