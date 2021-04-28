package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;

import java.util.ArrayList;
import java.util.List;

public class ResourceStorageBuilder {

	private GameItemsManager gameItemsManager;

	/**
	 * List of rules that the storage implements when the storage is created
	 */
	private List<ResourceStorageRule> rules = new ArrayList<>();

	/**
	 * Method to create new ResourceStorageBuilder
	 * @return ResourceStorageBuilder
	 */
	public static ResourceStorageBuilder initResourceStorageBuilder() {
		return new ResourceStorageBuilder();
	}

	/**
	 * Method to new ResourceStorage with its respective rules
	 * @param storageID storage identifier
	 * @return ResourceStorage new storage
	 */
	public ResourceStorage createResourceStorage(String storageID) {
		return new ResourceStorage(storageID, gameItemsManager, rules);
	}

	/**
	 * Method to one new rule to the list that contains all the rules of the storage
	 * @param rule new rule to add to the list that contains all the rules of the storage
	 * @return ResourceStorageBuilder
	 */
	public ResourceStorageBuilder addRule(ResourceStorageRule rule) {
		rules.add(rule);
		return this;
	}

}
