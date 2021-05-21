package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;

import java.util.List;

public class ServerGameHistoryRepresentation extends ServerRepresentation {

    public final List<ServerGameActionRepresentation> gameActions;

    public ServerGameHistoryRepresentation(
        @JsonProperty("gameActions") List<ServerGameActionRepresentation> gameActions
    ) {
        this.gameActions = gameActions;
    }

}
