package it.polimi.ingsw.server.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerMaxResourceNumberRuleRepresentation extends ServerResourceStorageRuleRepresentation {
	/**
	 * Max number of resources that the storage can contain
	 */
	public final int maxResources;

	public ServerMaxResourceNumberRuleRepresentation(
		@JsonProperty("maxResources") int maxResources
	) {
		this.maxResources = maxResources;
	}

}
