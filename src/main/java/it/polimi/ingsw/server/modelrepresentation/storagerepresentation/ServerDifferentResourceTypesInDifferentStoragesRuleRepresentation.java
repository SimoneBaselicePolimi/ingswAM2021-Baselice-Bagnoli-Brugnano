package it.polimi.ingsw.server.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ServerDifferentResourceTypesInDifferentStoragesRuleRepresentation extends ServerResourceStorageRuleRepresentation {

	/**
	 * List of storages that implement this specific rule
	 */
	public final List<ServerResourceStorageRepresentation> storages;

	public ServerDifferentResourceTypesInDifferentStoragesRuleRepresentation(
		@JsonProperty("storages") List<ServerResourceStorageRepresentation> storages
	) {
		this.storages = storages;
	}
}
