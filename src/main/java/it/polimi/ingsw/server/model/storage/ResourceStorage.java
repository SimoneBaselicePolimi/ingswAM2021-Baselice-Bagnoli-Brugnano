package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceStorage {
	/**
	 * ID storage identificator
	 */
	private String storageID;
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
	protected ResourceStorage(List<ResourceStorageRule> rules, String storageID){
		this.rules = rules;
		this.storageID = storageID;
	}


	/**
	 * @return storage identificator
	 */
	public String getStorageID(){
		return storageID;
	}

	/**
	 *
	 * @param newResources
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
	 * @param resources
	 * @return true if it is possible to remove new resources from the storage
	 */
	public boolean canRemoveResources (Map<ResourceType,Integer> resources){
		for (ResourceType resource : resources.keySet()) {
			if (!this.resources.containsKey(resource) || this.resources.get(resource) < resources.get(resource))
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
	 * @param resources Map of resources that the method removes from the storage
	 * @return newResources Map of resources that the method removes from the storage
	 * @throws NotEnoughResourcesException if there aren't enough resources to remove
	 */
	public Map<ResourceType, Integer> removeResources(Map<ResourceType, Integer> resources)
			throws NotEnoughResourcesException {
		if(!canRemoveResources(resources))
			throw new NotEnoughResourcesException();
		for (ResourceType resource : resources.keySet()) {
			if (this.resources.get(resource) == resources.get(resource))
				this.resources.remove(resource);
			else
				this.resources.put(resource, this.resources.get(resource) - resources.get(resource));
		}
		return resources;
	}


	/**
	 * @return Type and number of resources in the storage
	 */
	public Map<ResourceType, Integer> peekResources() {
		return new HashMap<>(this.resources);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ResourceStorage))
			return false;
		ResourceStorage m = (ResourceStorage) o;
			return (storageID.equals(m.storageID));
	}

	@Override
	public int hashCode() {
		return storageID.hashCode();
	}
}
