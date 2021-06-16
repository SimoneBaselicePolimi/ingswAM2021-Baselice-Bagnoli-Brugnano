package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.List;

public class ClientDiscardedResourcesMarketActionRepresentation extends ClientGameActionRepresentation {

    @SerializeIdOnly
    public final Player player;

    public final int numberOfResourcesDiscarded;

    public ClientDiscardedResourcesMarketActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("numberOfResourcesDiscarded") int numberOfResourcesDiscarded
    ) {
        this.player = player;
        this.numberOfResourcesDiscarded = numberOfResourcesDiscarded;
    }

    @JsonIgnore
    @Override
    public List<FormattedChar> getActionMessage() {
        if (numberOfResourcesDiscarded == 1)
            return FormattedChar.convertStringToFormattedCharList(
                Localization.getLocalizationInstance().getString(
                "gameHistory.gameState.manageResourcesFromMarketTurn. discardedResourcesMarketAction.singular",
                player
                ));
        else
            return FormattedChar.convertStringToFormattedCharList(
                Localization.getLocalizationInstance().getString(
                "gameHistory.gameState.manageResourcesFromMarketTurn. discardedResourcesMarketAction.plural",
                player.getName(),
                numberOfResourcesDiscarded
            ));
    }

}
