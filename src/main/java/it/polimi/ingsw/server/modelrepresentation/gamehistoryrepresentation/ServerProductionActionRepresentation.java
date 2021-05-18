package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerProductionRepresentation;

import java.util.Set;

public class ServerProductionActionRepresentation extends ServerGameActionRepresentation {
    public final ServerPlayerRepresentation player;
    public final Set<ServerProductionRepresentation> productions;

    public ServerProductionActionRepresentation(
        ServerPlayerRepresentation player,
        Set<ServerProductionRepresentation> productions
    ) {
        this.player = player;
        this.productions = productions;
    }
}
