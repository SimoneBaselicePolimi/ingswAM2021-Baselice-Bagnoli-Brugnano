package it.polimi.ingsw.server.model.storage;

import java.util.ArrayList;
import java.util.List;

public class ResourceStorageBuilder {
	/**
	 * List of rules that the storage implements when the storage is created
	 */
	private List<ResourceStorageRule> rules = new ArrayList<>();;

	/**
	 * Create new ResourceStorageBuilder
	 * @return ResourceStorageBuilder
	 */
	public static ResourceStorageBuilder initResourceStorageBuilder() {
		return new ResourceStorageBuilder();
	}

	/**
	 * Create new ResourceStorage with its respective rules
	 * @return ResourceStorage
	 */
	public ResourceStorage createResourceStorage(String storageID) {
		return new ResourceStorage(rules, storageID);
	}

	/**
	 * Add one new rule to the list that contains all the rules of the storage
	 * @param rule
	 * @return ResourceStorageBuilder
	 */
	public ResourceStorageBuilder addRule(ResourceStorageRule rule) {
		rules.add(rule);
		return this;
	}

}
