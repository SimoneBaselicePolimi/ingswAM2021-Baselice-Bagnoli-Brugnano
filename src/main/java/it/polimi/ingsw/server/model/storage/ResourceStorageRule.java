package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public abstract class ResourceStorageRule {

	public abstract boolean checkRule(ResourceStorage storage, Map<ResourceType, Integer> newResources);

}
