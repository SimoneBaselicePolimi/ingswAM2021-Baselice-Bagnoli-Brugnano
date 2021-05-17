package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

public class ServerDiscardedResourcesMarketActionRepresentation extends ServerGameActionRepresentation {

    public final ServerPlayerRepresentation player;
    public final int numberOfResourcesDiscarded;

    public ServerDiscardedResourcesMarketActionRepresentation(
        ServerPlayerRepresentation player,
        int numberOfResourcesDiscarded
    ) {
        this.player = player;
        this.numberOfResourcesDiscarded = numberOfResourcesDiscarded;
    }
}
