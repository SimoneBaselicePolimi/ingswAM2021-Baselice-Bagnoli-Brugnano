package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;
import it.polimi.ingsw.server.modelrepresentation.storagerepresentation.ServerResourceStorageRuleRepresentation;

import java.util.Map;

/**
 * A ResourceStorageRule is a feature that is implemented by a storage: storages implement different rules according
 * to their needs (space, types of resources ...). Starting from these rules, storages of any type can be built.
 */
public abstract class ResourceStorageRule implements Representable<ServerResourceStorageRuleRepresentation> {

	public void initializeRule(ResourceStorage storage) {}

	public abstract boolean checkRule(ResourceStorage storage, Map<ResourceType, Integer> newResources);

}
