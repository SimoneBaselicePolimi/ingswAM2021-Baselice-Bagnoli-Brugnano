package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class SameResourceTypeRule extends ResourceStorageRule {

	/**
	 * SameResourceTypeRule Constructor
	 */
	public SameResourceTypeRule() {
	}

	@Override
	public boolean checkRule(ResourceStorage storage, Map<ResourceType,Integer> newResources) {
		if (newResources.size() > 1)
			return false;
		if (newResources.isEmpty() || (storage.peekResources().isEmpty() && newResources.size() == 1))
			return true;
		for (ResourceType resourceInStorage : storage.peekResources().keySet()){
			for (ResourceType resourceToAdd : newResources.keySet()) {
				if (resourceInStorage != resourceToAdd)
					return false;
			}
		}
		return true;
	}

}
