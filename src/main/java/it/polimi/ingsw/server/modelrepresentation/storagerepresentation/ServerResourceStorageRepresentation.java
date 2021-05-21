package it.polimi.ingsw.server.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerRegisteredIdentifiableItemRepresentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerResourceStorageRepresentation extends ServerRegisteredIdentifiableItemRepresentation {

    /**
     * List of rules that the storage implements
     */
    public final List<ServerResourceStorageRuleRepresentation> rules;

    /**
     * Type and number of resources that are present in the storage
     */
    public final Map<ResourceType,Integer> resources;

    public ServerResourceStorageRepresentation(
        @JsonProperty("itemID") String itemID,
        @JsonProperty("rules") List<ServerResourceStorageRuleRepresentation> rules,
        @JsonProperty("resources") Map<ResourceType, Integer> resources
    ) {
        super(itemID);
        this.rules = rules;
        this.resources = new HashMap<>(resources);
    }
}
