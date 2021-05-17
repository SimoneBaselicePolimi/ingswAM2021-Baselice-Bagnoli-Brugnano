package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

import java.util.Set;

public class ServerProductionActionRepresentation extends ServerGameActionRepresentation {
    public final ServerPlayerRepresentation player;
    public final Set<ServerGameActionRepresentation> productions;

    public ServerProductionActionRepresentation(
        ServerPlayerRepresentation player,
        Set<ServerGameActionRepresentation> productions
    ) {
        this.player = player;
        this.productions = productions;
    }
}
