package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceStorage {
	/**
	 * List of rules that the storage implements
	 */
	private List<ResourceStorageRule> rules;
	/**
	 * Type and number of resources that are present in the storage
	 */
	private Map<ResourceType,Integer> resources = new HashMap<>();;

	/**
	 * ResourceStorage Constructor
	 * @param rules
	 */
	protected ResourceStorage(List<ResourceStorageRule> rules){
		this.rules = rules;
	}

	/**
	 *
	 * @param newResources
	 * @return true if it is possible to add new resources to the storage
	 */
	public boolean canAddResources (Map<ResourceType,Integer> newResources){
		for(ResourceStorageRule rule : rules) {
			if (!rule.checkRule(this, resources))
				return false;
		}
		return true;
	}

	/**
	 * @param newResources
	 * @return true if it is possible to remove new resources from the storage
	 */
	public boolean canRemoveResources (Map<ResourceType,Integer> newResources){
		for (ResourceType resource : newResources.keySet()) {
			if (!this.resources.containsKey(resource) || this.resources.get(resource) < newResources.get(resource))
				return false;
		}
		return true;
	}

	/**
	 * Add new resources (type and number) in the storage
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
	 * Remove some resources (type and number) from the storage
	 * @param newResources Map of resources that the method removes from the storage
	 * @return newResources Map of resources that the method removes from the storage
	 * @throws NotEnoughResourcesException if there aren't enough resources to remove
	 */
	public Map<ResourceType, Integer> removeResources(Map<ResourceType, Integer> newResources)
			throws NotEnoughResourcesException {
		if(!canRemoveResources(newResources))
			throw new NotEnoughResourcesException();
		for (ResourceType resource : newResources.keySet()) {
			if (this.resources.get(resource) == newResources.get(resource))
				this.resources.remove(resource);
			else
				this.resources.put(resource, this.resources.get(resource) - newResources.get(resource));
		}
		return newResources;
	}


	/**
	 * @return Type and number of resources in the storage
	 */
	public Map<ResourceType, Integer> peekResources() {
		return this.resources;
	}

}
