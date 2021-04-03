package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

/**
 * a ResourceStorageRule is a feature that is implemented by a storage: storages implement different rules according
 * to their needs (space, types of resources ...). Starting from these rules, storages of any type can be built.
 */
public abstract class ResourceStorageRule {

	public abstract boolean checkRule(ResourceStorage storage, Map<ResourceType, Integer> newResources);

}
