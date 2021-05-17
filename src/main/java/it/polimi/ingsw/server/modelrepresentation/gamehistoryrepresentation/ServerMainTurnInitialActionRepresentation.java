package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

public class ServerMainTurnInitialActionRepresentation extends ServerGameActionRepresentation {
    public final ServerPlayerRepresentation player;

    public ServerMainTurnInitialActionRepresentation(ServerPlayerRepresentation player) {
        this.player = player;
    }

}
