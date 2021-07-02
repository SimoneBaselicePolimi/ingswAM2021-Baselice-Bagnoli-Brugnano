package it.polimi.ingsw.server.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerRegisteredIdentifiableItemRepresentation;

import java.util.List;
import java.util.stream.Collectors;

public class ServerDifferentResourceTypesInDifferentStoragesRuleRepresentation extends ServerResourceStorageRuleRepresentation {

	/**
	 * List of storages that implement this specific rule
	 */

	public final List<String> storagesID;

	public ServerDifferentResourceTypesInDifferentStoragesRuleRepresentation(
		@JsonProperty("storagesID") List<String> storagesID
	) {
		this.storagesID = storagesID;
	}
}
