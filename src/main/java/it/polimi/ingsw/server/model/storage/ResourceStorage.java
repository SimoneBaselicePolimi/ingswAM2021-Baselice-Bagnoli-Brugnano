package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceStorage {
	private List<ResourceStorageRule> rules;
	private Map<ResourceType,Integer> resources = new HashMap<>();;

	protected ResourceStorage(List<ResourceStorageRule> rules){
		this.rules = rules;
	}

	public void addResources(Map<ResourceType,Integer> newResources) throws ResourceStorageRuleViolationException {
		checkRules(newResources);
		for (ResourceType resource : newResources.keySet()) {
			if (this.resources.containsKey(resource))
				this.resources.put(resource, this.resources.get(resource) + newResources.get(resource));
			else
				this.resources.put(resource, newResources.get(resource));
		}
	}

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

	private void checkRules(Map<ResourceType, Integer> resources) throws ResourceStorageRuleViolationException{
		for(ResourceStorageRule rule : rules){
			if (!rule.checkRule(this, resources))
				throw new ResourceStorageRuleViolationException();
		}
	}

	public Map<ResourceType, Integer> peekResources() {
		return this.resources;
	}

}
