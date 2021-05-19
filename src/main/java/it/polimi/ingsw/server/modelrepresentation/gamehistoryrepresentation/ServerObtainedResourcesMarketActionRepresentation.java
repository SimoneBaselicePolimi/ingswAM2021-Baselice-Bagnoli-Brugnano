package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

import java.util.Map;

public class ServerObtainedResourcesMarketActionRepresentation extends ServerGameActionRepresentation {
    public final ServerPlayerRepresentation player;
    public final Map<ResourceType, Integer> resourcesObtained;

    public ServerObtainedResourcesMarketActionRepresentation(
        ServerPlayerRepresentation player,
        Map<ResourceType, Integer> resourcesObtained
    ) {
        this.player = player;
        this.resourcesObtained = resourcesObtained;
    }
}
