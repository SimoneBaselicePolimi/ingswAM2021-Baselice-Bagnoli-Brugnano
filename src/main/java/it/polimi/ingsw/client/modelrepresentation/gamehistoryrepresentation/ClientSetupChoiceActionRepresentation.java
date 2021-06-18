package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.List;
import java.util.Map;

public class ClientSetupChoiceActionRepresentation extends ClientGameActionRepresentation {

    @SerializeIdOnly
    public final Player player;

    public final Map<ResourceType,Integer> initialResources;

    public ClientSetupChoiceActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("initialResources") Map<ResourceType, Integer> initialResources
    ) {
        this.player = player;
        this.initialResources = initialResources;
    }

    @JsonIgnore
    @Override
    public List<FormattedChar> getActionMessage() {
        if (initialResources.isEmpty()) {
            return FormattedChar.convertStringToFormattedCharList(
                Localization.getLocalizationInstance().getString(
                "gameHistory.gameState.setup.choiceMessageWithoutInitialResources",
                player.getName()
            ));
        } else {
            return FormattedChar.convertStringToFormattedCharList(
                Localization.getLocalizationInstance().getString(
                    "gameHistory.gameState.setup.choiceMessageWithInitialResources",
                    player.getName(),
                    LocalizationUtils.getResourcesListAsCompactString(initialResources)
                )
            );
        }
    }

}
