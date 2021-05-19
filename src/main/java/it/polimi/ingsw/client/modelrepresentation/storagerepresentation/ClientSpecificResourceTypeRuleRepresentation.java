package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

public class ClientSpecificResourceTypeRuleRepresentation extends ClientResourceStorageRuleRepresentation {

	/**
	 * Specific type of resource that the storage can contain
	 */
	private final ResourceType resourceType;

	public ClientSpecificResourceTypeRuleRepresentation(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}
}
