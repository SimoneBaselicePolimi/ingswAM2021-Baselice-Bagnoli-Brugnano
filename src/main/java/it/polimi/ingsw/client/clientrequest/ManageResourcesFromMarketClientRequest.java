package it.polimi.ingsw.client.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class ManageResourcesFromMarketClientRequest extends ClientRequest {

    public final Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> resourcesToAddByStorage;

    public final Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> starResourcesChosenToAddByStorage;

    public final Map<ResourceType, Integer> resourcesLeftInTemporaryStorage;

    public ManageResourcesFromMarketClientRequest(
        @JsonProperty("player") Player player,
        @JsonProperty("resourcesToAddByStorage") Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> resourcesToAddByStorage,
        @JsonProperty("starResourcesChosenToAddByStorage") Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> starResourcesChosenToAddByStorage,
        @JsonProperty("resourcesLeftInTemporaryStorage") Map<ResourceType, Integer> resourcesLeftInTemporaryStorage
    ) {
        super(player);
        this.resourcesToAddByStorage = resourcesToAddByStorage;
        this.starResourcesChosenToAddByStorage = starResourcesChosenToAddByStorage;
        this.resourcesLeftInTemporaryStorage = resourcesLeftInTemporaryStorage;
    }

}
