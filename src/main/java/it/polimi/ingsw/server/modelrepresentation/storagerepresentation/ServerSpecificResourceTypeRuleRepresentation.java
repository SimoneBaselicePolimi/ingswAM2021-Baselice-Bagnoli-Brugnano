package it.polimi.ingsw.server.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

public class ServerSpecificResourceTypeRuleRepresentation extends ServerResourceStorageRuleRepresentation {

	/**
	 * Specific type of resource that the storage can contain
	 */
	public final ResourceType resourceType;

	public ServerSpecificResourceTypeRuleRepresentation(
		@JsonProperty("resourceType") ResourceType resourceType
	) {
		this.resourceType = resourceType;
	}

}
