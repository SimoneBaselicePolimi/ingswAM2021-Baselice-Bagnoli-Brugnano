package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

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

	@Override
	public Optional<String> getDescription() {
		return Optional.empty();
	}

}
