package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.modelrepresentation.storagerepresentation.ServerResourceStorageRepresentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represent a storage that contains resources
 */
public class ResourceStorageImp extends RegisteredIdentifiableItem implements ResourceStorage {

	/**
	 * List of rules that the storage implements
	 */
	public final List<ResourceStorageRule> rules;
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
	public ResourceStorageImp(String resourceStorageID, GameItemsManager gameItemsManager, List<ResourceStorageRule> rules){
		super(resourceStorageID, gameItemsManager, ResourceStorage.class);
		this.rules = rules;
		rules.forEach(r -> r.initializeRule(this));
	}

	/**
	 * Method to add new resources to the storage
	 * @param newResources resources to add
	 * @return true if it is possible to add new resources to the storage
	 */
	@Override
	public boolean canAddResources(Map<ResourceType, Integer> newResources){
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
	@Override
	public boolean canRemoveResources(Map<ResourceType, Integer> resourcesToRemove){
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
	@Override
	public void addResources(Map<ResourceType, Integer> newResources) throws ResourceStorageRuleViolationException {
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
	@Override
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
	@Override
	public Map<ResourceType, Integer> peekResources() {
		return new HashMap<>(this.resources);
	}

	@Override
	public List<ResourceStorageRule> getRules() {
		return rules;
	}

	@Override
	public ServerResourceStorageRepresentation getServerRepresentation() {
		return new ServerResourceStorageRepresentation(
			getItemID(),
			rules.stream().map(r -> r.getServerRepresentation()).collect(Collectors.toList()),
			resources
		);
	}

	@Override
	public ServerResourceStorageRepresentation getServerRepresentationForPlayer(Player player) {
		return getServerRepresentation();
	}
}
