package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ClientDifferentResourceTypesInDifferentStoragesRuleRepresentation extends ClientResourceStorageRuleRepresentation {

	/**
	 * List of storages that implement this specific rule
	 */
	private final List<ClientResourceStorageRepresentation> storages;

	public ClientDifferentResourceTypesInDifferentStoragesRuleRepresentation(
		@JsonProperty("storages") List<ClientResourceStorageRepresentation> storages
	) {
		this.storages = storages;
	}

	public List<ClientResourceStorageRepresentation> getStorages() {
		return storages;
	}
}
