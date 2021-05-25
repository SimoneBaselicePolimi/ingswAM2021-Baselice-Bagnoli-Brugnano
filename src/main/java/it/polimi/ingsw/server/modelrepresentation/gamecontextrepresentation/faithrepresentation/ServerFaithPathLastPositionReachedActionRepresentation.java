package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;

public class ServerFaithPathLastPositionReachedActionRepresentation extends ServerGameActionRepresentation {

    public final Player player;

    public ServerFaithPathLastPositionReachedActionRepresentation(
        @JsonProperty("player") Player player
    ) {
        this.player = player;
    }
}
