package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represent a storage that contains resources
 */
public class ResourceStorage extends RegisteredIdentifiableItem {

	/**
	 * List of rules that the storage implements
	 */
	private List<ResourceStorageRule> rules;
	/**
	 * Type and number of resources that are present in the storage
	 */
	private Map<ResourceType,Integer> resources = new HashMap<>();

	/**
	 * ResourceStorage Constructor
	 * @param resourceStorageID ID which identifies this specific Resource Storage
	 * @param gameItemsManager a reference to gameItemsManager is needed to register the new ResourceStorage object
	 *                          (see {@link RegisteredIdentifiableItem})
	 * @param rules that the storage implements
	 */
	protected ResourceStorage(String resourceStorageID, GameItemsManager gameItemsManager, List<ResourceStorageRule> rules){
		super(resourceStorageID, gameItemsManager);
		this.rules = rules;
	}

	/**
	 * Method to add new resources to the storage
	 * @param newResources resources to add
	 * @return true if it is possible to add new resources to the storage
	 */
	public boolean canAddResources (Map<ResourceType,Integer> newResources){
		for(ResourceStorageRule rule : rules) {
			if (!rule.checkRule(this, newResources))
				return false;
		}
		return true;
	}

	/**
	 * Method to remove resources from the storage
	 * @param resourcesToRemove resources to remove
	 * @return true if it is possible to remove resources from the storage (there are enough resources to remove)
	 */
	public boolean canRemoveResources (Map<ResourceType,Integer> resourcesToRemove){
		for (ResourceType resource : resourcesToRemove.keySet()) {
			if (!this.resources.containsKey(resource) || this.resources.get(resource) < resourcesToRemove.get(resource))
				return false;
		}
		return true;
	}

	/**
	 * Method to add new resources (type and number) in the storage
	 * @param newResources Map of resources that the method adds to the storage
	 * @throws ResourceStorageRuleViolationException if new resources don't respect rules of the storage
	 */
	public void addResources(Map<ResourceType,Integer> newResources) throws ResourceStorageRuleViolationException {
		if (!canAddResources(newResources))
			throw new ResourceStorageRuleViolationException();
		for (ResourceType resource : newResources.keySet()) {
			if (this.resources.containsKey(resource))
				this.resources.put(resource, this.resources.get(resource) + newResources.get(resource));
			else
				this.resources.put(resource, newResources.get(resource));
		}
	}

	/**
	 * Method to remove some resources (type and number) from the storage
	 * @param resourcesToRemove Map of resources that the method removes from the storage
	 * @return resourcesToRemove Map of resources that the method removes from the storage
	 * @throws NotEnoughResourcesException if there aren't enough resources to remove
	 */
	public Map<ResourceType, Integer> removeResources(Map<ResourceType, Integer> resourcesToRemove)
			throws NotEnoughResourcesException {
		if(!canRemoveResources(resourcesToRemove))
			throw new NotEnoughResourcesException();
		for (ResourceType resource : resourcesToRemove.keySet()) {
			if (this.resources.get(resource).equals(resourcesToRemove.get(resource)))
				this.resources.remove(resource);
			else
				this.resources.put(resource, this.resources.get(resource) - resourcesToRemove.get(resource));
		}
		return resourcesToRemove;
	}

	/**
	 * Method to  Method to peek resources present in the storage
	 * @return Type and number of resources in the storage
	 */
	public Map<ResourceType, Integer> peekResources() {
		return new HashMap<>(this.resources);
	}

}
