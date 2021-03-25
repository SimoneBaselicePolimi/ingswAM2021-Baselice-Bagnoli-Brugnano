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
	 * Add new resources (type and number) in resources that contains all the resources of the storage
	 * @param newResources Map of resources that the method adds to the storage
	 * @throws ResourceStorageRuleViolationException if new resources don't respect rules of the storage
	 */
	public void addResources(Map<ResourceType,Integer> newResources) throws ResourceStorageRuleViolationException {
		checkRules(newResources);
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
		for (ResourceType resource : newResources.keySet()) {
			if (!this.resources.containsKey(resource))
				throw new NotEnoughResourcesException();
			else if (this.resources.get(resource) < newResources.get(resource))
				throw new NotEnoughResourcesException();
			else if (this.resources.get(resource) == newResources.get(resource))
				this.resources.remove(resource);
			else
				this.resources.put(resource, this.resources.get(resource) - newResources.get(resource));
		}
		return newResources;
	}

	/**
	 * check if new resources respect rules of the storage
	 * @param resources
	 * @throws ResourceStorageRuleViolationException if new resources don't respect rules of the storage
	 */
	private void checkRules(Map<ResourceType, Integer> resources) throws ResourceStorageRuleViolationException{
		for(ResourceStorageRule rule : rules){
			if (!rule.checkRule(this, resources))
				throw new ResourceStorageRuleViolationException();
		}
	}

	/**
	 * @return Type and number of resources in the storage
	 */
	public Map<ResourceType, Integer> peekResources() {
		return this.resources;
	}

}
