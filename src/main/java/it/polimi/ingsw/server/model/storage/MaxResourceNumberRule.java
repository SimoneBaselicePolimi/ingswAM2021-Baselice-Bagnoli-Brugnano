package it.polimi.ingsw.server.model.storage;


import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class MaxResourceNumberRule extends ResourceStorageRule {

	public MaxResourceNumberRule(int maxResources) {

	}

	@Override
	public boolean checkRule(ResourceStorage storage, Map<ResourceType,Integer> newResources) {
		return false;
	}

}