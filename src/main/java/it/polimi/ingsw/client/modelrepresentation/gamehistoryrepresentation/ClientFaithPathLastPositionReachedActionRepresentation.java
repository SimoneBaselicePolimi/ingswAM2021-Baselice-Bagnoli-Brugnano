package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.List;

public class ClientFaithPathLastPositionReachedActionRepresentation extends ClientGameActionRepresentation {

    @SerializeIdOnly
    private final Player player;

    public ClientFaithPathLastPositionReachedActionRepresentation(
        @JsonProperty("player") Player player
    ) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @JsonIgnore
    @Override
    public List<FormattedChar> getActionMessage() {
        return FormattedChar.convertStringToFormattedCharList(Localization.getLocalizationInstance().getString(
            "gameHistory.faithPath.lastPositionReached",
            player.getName()
        ));
    }
}
