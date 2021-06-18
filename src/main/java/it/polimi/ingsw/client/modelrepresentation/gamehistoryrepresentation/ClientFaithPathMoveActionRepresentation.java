package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientFaithPathMoveActionRepresentation extends ClientGameActionRepresentation {

    @SerializeIdOnly
    public final Player player;

    public final int steps;

    public ClientFaithPathMoveActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("steps") int steps
    ) {
        this.player = player;
        this.steps = steps;
    }

    public Player getPlayer() {
        return player;
    }

    public int getSteps() {
        return steps;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        if (steps == 1)
            return Localization.getLocalizationInstance().getString(
                "gameHistory.faithPath.playerMove.singular",
                player.getName()
            );
        else
            return Localization.getLocalizationInstance().getString(
                "gameHistory.faithPath.playerMove.plural",
                player.getName(),
                steps
            );
    }

}
