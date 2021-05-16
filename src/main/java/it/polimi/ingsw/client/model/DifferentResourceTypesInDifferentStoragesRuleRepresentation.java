package it.polimi.ingsw.client.model;

import java.util.List;

public class DifferentResourceTypesInDifferentStoragesRuleRepresentation extends ResourceStorageRuleRepresentation {
	/**
	 * List of storages that implement this specific rule
	 */
	private final List<ResourceStorageRepresentation> storages;

	public DifferentResourceTypesInDifferentStoragesRuleRepresentation(List<ResourceStorageRepresentation> storages) {
		this.storages = storages;
	}

	public List<ResourceStorageRepresentation> getStorages() {
		return storages;
	}
}
