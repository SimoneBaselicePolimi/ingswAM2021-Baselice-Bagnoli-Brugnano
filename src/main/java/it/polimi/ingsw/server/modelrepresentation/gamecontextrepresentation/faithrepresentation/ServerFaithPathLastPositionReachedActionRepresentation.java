package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;

public class ServerFaithPathLastPositionReachedActionRepresentation extends ServerRepresentation {

    public final Player player;

    public ServerFaithPathLastPositionReachedActionRepresentation(
        @JsonProperty("player") Player player
    ) {
        this.player = player;
    }
}
