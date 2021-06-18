package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

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
    public String getActionMessage() {
        if (numberOfResourcesDiscarded == 1)
            return Localization.getLocalizationInstance().getString(
                "gameHistory.gameState.manageResourcesFromMarketTurn.discardedResourcesMarketAction.singular",
                player.getName()
            );
        else
            return Localization.getLocalizationInstance().getString(
                "gameHistory.gameState.manageResourcesFromMarketTurn.discardedResourcesMarketAction.plural",
                player.getName(),
                numberOfResourcesDiscarded
            );
    }

}
