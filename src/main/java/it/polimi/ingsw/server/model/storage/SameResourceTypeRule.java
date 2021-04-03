package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

/**
 * If the storage implements this rule, the storage can only contain resources that are
 * equal to each other (different type of resources cannot be present)
 */
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
		return newResources.isEmpty() ||
				storage.peekResources().isEmpty() ||
				storage.peekResources().keySet().iterator().next() == newResources.keySet().iterator().next();
	}

}
