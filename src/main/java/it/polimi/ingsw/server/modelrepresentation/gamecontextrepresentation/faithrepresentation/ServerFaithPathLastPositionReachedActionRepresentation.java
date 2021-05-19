package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;

public class ServerFaithPathLastPositionReachedActionRepresentation extends ServerGameActionRepresentation {
    public final ServerPlayerRepresentation player;

    public ServerFaithPathLastPositionReachedActionRepresentation(ServerPlayerRepresentation player) {
        this.player = player;
    }
}
