package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRequirementRepresentation;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

public class ClientResourceNumberRequirementRepresentation extends ClientLeaderCardRequirementRepresentation {

    ResourceType resourceType;
    int resourceNumber;

    /**
     * ResourceNumberRequirementRepresentation constructor
     * @param resourceType type of resource required
     * @param resourceNumber number of resource required
     */
    public ClientResourceNumberRequirementRepresentation(ResourceType resourceType, int resourceNumber) {
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
