package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;

import java.util.List;

public class ServerGameHistoryRepresentation extends ServerRepresentation {

    public final List<ServerGameActionRepresentation> gameActions;

    public ServerGameHistoryRepresentation(List<ServerGameActionRepresentation> gameActions) {
        this.gameActions = gameActions;
    }

}
