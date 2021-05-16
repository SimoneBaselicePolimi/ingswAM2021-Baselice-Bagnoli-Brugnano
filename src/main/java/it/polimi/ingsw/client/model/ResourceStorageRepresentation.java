package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.storage.ResourceStorageRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceStorageRepresentation extends RegisteredIdentifiableItemRepresentation{

    /**
     * List of rules that the storage implements
     */
    private final List<ResourceStorageRuleRepresentation> rules;

    /**
     * Type and number of resources that are present in the storage
     */
    private Map<ResourceType,Integer> resources;

    protected ResourceStorageRepresentation(
        String itemID,
        GameItemsManager gameItemsManager,
        List<ResourceStorageRuleRepresentation> rules,
        Map<ResourceType, Integer> resources
    ) {
        super(itemID, gameItemsManager);
        this.rules = rules;
        this.resources = new HashMap<>(resources);
    }

    public List<ResourceStorageRuleRepresentation> getRules() {
        return rules;
    }

    public Map<ResourceType, Integer> getResources() {
        return resources;
    }

    public void setResources(Map<ResourceType, Integer> resources) {
        this.resources = new HashMap<>(resources);
    }
}
