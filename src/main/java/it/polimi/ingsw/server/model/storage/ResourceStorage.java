package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.List;
import java.util.Map;

public class ResourceStorage {

	protected ResourceStorage(List<ResourceStorageRule> rules) {
	}

	public void addResources(Map<ResourceType,Integer> resources) throws ResourceStorageRuleViolationException {

	}

	public Map<ResourceType, Integer> removeResources(Map<ResourceType, Integer> resources)
			throws NotEnoughResourcesException {
		return null;
	}

	public Map<ResourceType, Integer> peekResources() {
		return null;
	}

}
