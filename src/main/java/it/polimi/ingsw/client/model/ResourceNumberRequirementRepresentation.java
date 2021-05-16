package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

public class ResourceNumberRequirementRepresentation extends LeaderCardRequirementRepresentation{

    ResourceType resourceType;
    int resourceNumber;

    /**
     * ResourceNumberRequirementRepresentation constructor
     * @param resourceType type of resource required
     * @param resourceNumber number of resource required
     */
    public ResourceNumberRequirementRepresentation(ResourceType resourceType, int resourceNumber) {
        this.resourceType = resourceType;
        this.resourceNumber = resourceNumber;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getResourceNumber() {
        return resourceNumber;
    }
}
