package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

public class SpecificResourceTypeRuleRepresentation extends ResourceStorageRuleRepresentation {

	/**
	 * Specific type of resource that the storage can contain
	 */
	private final ResourceType resourceType;

	public SpecificResourceTypeRuleRepresentation(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}
}
