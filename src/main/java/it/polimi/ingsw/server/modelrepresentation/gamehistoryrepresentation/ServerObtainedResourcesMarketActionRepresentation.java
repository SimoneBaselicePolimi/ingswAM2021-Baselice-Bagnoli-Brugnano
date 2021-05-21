package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

import java.util.Map;

public class ServerObtainedResourcesMarketActionRepresentation extends ServerGameActionRepresentation {

    public final ServerPlayerRepresentation player;
    public final Map<ResourceType, Integer> resourcesObtained;

    public ServerObtainedResourcesMarketActionRepresentation(
        @JsonProperty("player") ServerPlayerRepresentation player,
        @JsonProperty("resourcesObtained") Map<ResourceType, Integer> resourcesObtained
    ) {
        this.player = player;
        this.resourcesObtained = resourcesObtained;
    }
}
