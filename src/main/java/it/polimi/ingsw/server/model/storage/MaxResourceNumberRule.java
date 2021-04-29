package it.polimi.ingsw.server.model.storage;


import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;
/**
 * If the storage implements this rule, the storage has a maximum number of resources (ie it has a limited space).
 * If the storage is full, it is not possible to add resources until some are removed.
 */
public class MaxResourceNumberRule extends ResourceStorageRule {
	/**
	 * Max number of resources that the storage can contain
	 */
	private final int maxResources;

	/**
	 * MaxResourceNumberRule Constructor
	 * @param maxResources Max number of resources that the storage can contain
	 */
	public MaxResourceNumberRule(int maxResources) {
		this.maxResources = maxResources;
	}

	/**
	 * Method to verify if new resources can be added to the storage
	 * @param storage on which to verify that the rules are respected
	 * @param newResources resources to add
	 * @return true if the storage can contain the new resources
	 */
	@Override
	public boolean checkRule(ResourceStorage storage, Map<ResourceType,Integer> newResources) {
		Integer sumResourcesStorage = 0;
		Integer sumNewResources = 0;
		for (Integer number : storage.peekResources().values())
			sumResourcesStorage += number;
		for (Integer number : newResources.values())
			sumNewResources += number;
		if (sumResourcesStorage + sumNewResources > maxResources)
			return false;
		return true;
	}

}
