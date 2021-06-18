package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientMainTurnInitialActionRepresentation extends ClientGameActionRepresentation {

    @SerializeIdOnly
    public final Player player;

    public ClientMainTurnInitialActionRepresentation(
        @JsonProperty("player") Player player
    ) {
        this.player = player;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.gameState.mainTurn.initialMessage",
            player.getName()
        );
    }

}
