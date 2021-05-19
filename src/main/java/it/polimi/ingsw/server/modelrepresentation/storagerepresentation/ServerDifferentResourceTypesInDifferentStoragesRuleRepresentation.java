package it.polimi.ingsw.server.modelrepresentation.storagerepresentation;

import java.util.List;

public class ServerDifferentResourceTypesInDifferentStoragesRuleRepresentation extends ServerResourceStorageRuleRepresentation {
	/**
	 * List of storages that implement this specific rule
	 */
	public final List<ServerResourceStorageRepresentation> storages;

	public ServerDifferentResourceTypesInDifferentStoragesRuleRepresentation(
		List<ServerResourceStorageRepresentation> storages
	) {
		this.storages = storages;
	}
}
