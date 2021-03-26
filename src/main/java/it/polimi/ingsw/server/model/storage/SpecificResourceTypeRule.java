package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class SpecificResourceTypeRule extends ResourceStorageRule {
	/**
	 * Specific type of resource that the storage can contain
	 */
	private ResourceType resourceType;

	/**
	 * SpecificResourceTypeRule Constructor
	 * @param resourceType
	 */
	public SpecificResourceTypeRule(ResourceType resourceType) {
		this.resourceType=resourceType;
	}

	@Override
	public boolean checkRule(ResourceStorage storage, Map<ResourceType,Integer> newResources) {
		if (newResources.isEmpty())
			return true;
		for (ResourceType resource : newResources.keySet()){
			if (resource != resourceType)
				return false;
		}
		return true;
	}

}
