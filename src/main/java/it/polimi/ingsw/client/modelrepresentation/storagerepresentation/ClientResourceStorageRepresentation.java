package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientRegisteredIdentifiableItemRepresentation;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientResourceStorageRepresentation extends ClientRegisteredIdentifiableItemRepresentation {

    /**
     * List of rules that the storage implements
     */
    private final List<ClientResourceStorageRuleRepresentation> rules;

    /**
     * Type and number of resources that are present in the storage
     */
    private Map<ResourceType,Integer> resources;

    protected ClientResourceStorageRepresentation(
        @JsonProperty("itemID") String itemID,
        @JacksonInject("gameItemsManager") GameItemsManager gameItemsManager,
        @JsonProperty("rules") List<ClientResourceStorageRuleRepresentation> rules,
        @JsonProperty("resources") Map<ResourceType, Integer> resources
    ) {
        super(itemID, gameItemsManager);
        this.rules = rules;
        this.resources = new HashMap<>(resources);
    }

    public List<ClientResourceStorageRuleRepresentation> getRules() {
        return rules;
    }

    public Map<ResourceType, Integer> getResources() {
        return resources;
    }

    public void setResources(Map<ResourceType, Integer> resources) {
        this.resources = new HashMap<>(resources);
    }
}
