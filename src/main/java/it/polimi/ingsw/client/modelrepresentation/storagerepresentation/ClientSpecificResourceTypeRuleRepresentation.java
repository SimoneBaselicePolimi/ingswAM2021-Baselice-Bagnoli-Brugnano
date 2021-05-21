package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

public class ClientSpecificResourceTypeRuleRepresentation extends ClientResourceStorageRuleRepresentation {

	/**
	 * Specific type of resource that the storage can contain
	 */
	private final ResourceType resourceType;

	public ClientSpecificResourceTypeRuleRepresentation(
		@JsonProperty("resourceType") ResourceType resourceType
	) {
		this.resourceType = resourceType;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}
}
