package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is a rule that "checks" multiple storages.
 * Storages that implement this rule cannot contain the same type of resources.
 * (example: Storages A and B implement this rule. If a type of resource is contained in storage A,
 * the same type of resource cannot be contained in storage B).
 */
public class DifferentResourceTypesInDifferentStoragesRule extends ResourceStorageRule {
	/**
	 * List of storages that implement this specific rule
	 */
	private List<ResourceStorage> storages = new ArrayList<>();

	/**
	 * DifferentResourceTypesInDifferentStoragesRule Constructor
	 */
	public DifferentResourceTypesInDifferentStoragesRule() {
	}

	/**
	 * Method to verify if new resources can be added to the storage
	 * @param storage on which to verify that the rules are respected
	 * @param newResources resources to add
	 * @return true if the storage can contain the new resources
	 */
	@Override
	public boolean checkRule(ResourceStorage storage, Map<ResourceType,Integer> newResources) {
			if (!storages.contains(storage))
				storages.add(storage);
			for (ResourceStorage s :  storages){
				for (ResourceType resourceInStorage: s.peekResources().keySet()) {
					if (!s.equals(storage)){
						for (ResourceType resourceType : newResources.keySet()) {
							if (resourceType.equals(resourceInStorage))
									return false;
						}
					}
				}
			}
			return true;
	}

}
