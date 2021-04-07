package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

/**
 * If the storage implements this rule, only a certain type of resources can be added to the storage
 * (e.g. if resourceType = COINS, only COINS can be added to the storage)
 */
public class SpecificResourceTypeRule extends ResourceStorageRule {
	/**
	 * Specific type of resource that the storage can contain
	 */
	private ResourceType resourceType;

	/**
	 * SpecificResourceTypeRule Constructor
	 * @param resourceType Specific type of resource that the storage can contain
	 */
	public SpecificResourceTypeRule(ResourceType resourceType) {
		this.resourceType=resourceType;
	}

	/**
	 *
	 * @param storage
	 * @param newResources
	 * @return
	 */
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
