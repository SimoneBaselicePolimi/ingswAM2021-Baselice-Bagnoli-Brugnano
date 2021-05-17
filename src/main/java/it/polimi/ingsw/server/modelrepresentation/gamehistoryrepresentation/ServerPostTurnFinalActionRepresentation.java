package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

public class ServerPostTurnFinalActionRepresentation extends ServerGameActionRepresentation {
    public final ServerPlayerRepresentation player;

    public ServerPostTurnFinalActionRepresentation(ServerPlayerRepresentation player) {
        this.player = player;
    }

}
