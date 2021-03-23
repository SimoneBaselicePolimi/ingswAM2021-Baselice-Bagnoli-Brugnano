package it.polimi.ingsw.model.storage;

import it.polimi.ingsw.model.gameitems.ResourceType;

import java.util.Map;

public class SameResourceTypeRule extends ResourceStorageRule {

	public SameResourceTypeRule() {

	}

	@Override
	public boolean checkRule(ResourceStorage storage, Map<ResourceType,Integer> newResources) {
		return false;
	}

}
