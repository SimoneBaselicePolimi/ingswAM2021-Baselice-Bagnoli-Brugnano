package it.polimi.ingsw.server.model.storage;


import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class MaxResourceNumberRule extends ResourceStorageRule {
	/**
	 * Max number of resources that the storage can contain
	 */
	private int maxResources;

	/**
	 * MaxResourceNumberRule Constructor
	 * @param maxResources
	 */
	public MaxResourceNumberRule(int maxResources) {
		this.maxResources = maxResources;
	}

	@Override
	public boolean checkRule(ResourceStorage storage, Map<ResourceType,Integer> newResources) {
		Integer sumResourcesStorage = 0;
		Integer sumNewResources = 0;
		if (newResources.isEmpty())
			return true;
		for (Integer number : storage.peekResources().values())
			sumResourcesStorage = sumResourcesStorage + number;
		for (Integer number : newResources.values())
			sumNewResources = sumNewResources + number;
		if (sumResourcesStorage + sumNewResources > maxResources)
			return false;
		return true;
	}

}
