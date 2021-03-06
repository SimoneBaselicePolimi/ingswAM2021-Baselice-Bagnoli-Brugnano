package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.modelrepresentation.storagerepresentation.ServerResourceStorageRuleRepresentation;
import it.polimi.ingsw.server.modelrepresentation.storagerepresentation.ServerSpecificResourceTypeRuleRepresentation;

import java.util.Map;

/**
 * If the storage implements this rule, only a certain type of resources can be added to the storage
 * (e.g. if resourceType = COINS, only COINS can be added to the storage)
 */
public class SpecificResourceTypeRule extends ResourceStorageRule {
	/**
	 * Specific type of resource that the storage can contain
	 */
	public final ResourceType resourceType;

	/**
	 * SpecificResourceTypeRule Constructor
	 * @param resourceType Specific type of resource that the storage can contain
	 */
	public SpecificResourceTypeRule(ResourceType resourceType) {
		this.resourceType=resourceType;
	}

	/**
	 * Method to verify if new resources can be added to the storage
	 * @param storage on which to verify that the rules are respected
	 * @param newResources resources to add
	 * @return true if the storage can contain the new resources
	 */
	@Override
	public boolean checkRule(ResourceStorage storage, Map<ResourceType,Integer> newResources) {
		if (newResources.isEmpty())
			return true;
		for (ResourceType resource : newResources.keySet()){
			if (resource != resourceType)
				return false;
		}
		return true;
	}

	@Override
	public ServerResourceStorageRuleRepresentation getServerRepresentation() {
		return new ServerSpecificResourceTypeRuleRepresentation(resourceType);
	}

	@Override
	public ServerResourceStorageRuleRepresentation getServerRepresentationForPlayer(Player player) {
		return getServerRepresentation();
	}
}
