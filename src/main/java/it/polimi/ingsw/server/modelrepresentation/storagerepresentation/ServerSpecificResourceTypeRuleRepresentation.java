package it.polimi.ingsw.server.modelrepresentation.storagerepresentation;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

public class ServerSpecificResourceTypeRuleRepresentation extends ServerResourceStorageRuleRepresentation {

	/**
	 * Specific type of resource that the storage can contain
	 */
	private final ResourceType resourceType;

	public ServerSpecificResourceTypeRuleRepresentation(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}
}
