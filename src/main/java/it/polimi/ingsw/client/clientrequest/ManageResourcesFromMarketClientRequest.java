package it.polimi.ingsw.client.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;

import java.util.Map;

public class ManageResourcesFromMarketClientRequest extends ClientRequest {

    @SerializeAsMapWithIdKey
    public final Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> resourcesInModifiedStorages;

    public final Map<ResourceType, Integer> resourcesLeftInTemporaryStorage;

    public ManageResourcesFromMarketClientRequest(
        @JsonProperty("player") Player player,
        @JsonProperty("resourcesInModifiedStorages") Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> resourcesInModifiedStorages,
        @JsonProperty("resourcesLeftInTemporaryStorage") Map<ResourceType, Integer> resourcesLeftInTemporaryStorage
    ) {
        super(player);
        this.resourcesInModifiedStorages = resourcesInModifiedStorages;
        this.resourcesLeftInTemporaryStorage = resourcesLeftInTemporaryStorage;
    }

}
