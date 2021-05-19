package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import java.util.List;

public class ClientDifferentResourceTypesInDifferentStoragesRuleRepresentation extends ClientResourceStorageRuleRepresentation {
	/**
	 * List of storages that implement this specific rule
	 */
	private final List<ClientResourceStorageRepresentation> storages;

	public ClientDifferentResourceTypesInDifferentStoragesRuleRepresentation(List<ClientResourceStorageRepresentation> storages) {
		this.storages = storages;
	}

	public List<ClientResourceStorageRepresentation> getStorages() {
		return storages;
	}
}
