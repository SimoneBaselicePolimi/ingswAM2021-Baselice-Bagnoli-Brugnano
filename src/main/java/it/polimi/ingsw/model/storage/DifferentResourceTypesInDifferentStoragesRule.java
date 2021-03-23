package it.polimi.ingsw.model.storage;

import it.polimi.ingsw.model.gameitems.ResourceType;

import java.util.Map;

public class DifferentResourceTypesInDifferentStoragesRule extends ResourceStorageRule {

	public DifferentResourceTypesInDifferentStoragesRule() {

	}

	@Override
	public boolean checkRule(ResourceStorage storage, Map<ResourceType,Integer> newResources) {
		return false;
	}

}
