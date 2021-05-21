package it.polimi.ingsw.server.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerLeaderCardRequirementRepresentation;

public class ServerResourceNumberRequirementRepresentation extends ServerLeaderCardRequirementRepresentation {

    public final ResourceType resourceType;
    public final int resourceNumber;

    /**
     * ResourceNumberRequirementRepresentation constructor
     * @param resourceType type of resource required
     * @param resourceNumber number of resource required
     */
    public ServerResourceNumberRequirementRepresentation(
        @JsonProperty("resourceType") ResourceType resourceType,
        @JsonProperty("resourceNumber") int resourceNumber
    ) {
        this.resourceType = resourceType;
        this.resourceNumber = resourceNumber;
    }
}
