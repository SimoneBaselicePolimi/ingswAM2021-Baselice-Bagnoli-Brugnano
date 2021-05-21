package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientMaxResourceNumberRuleRepresentation extends ClientResourceStorageRuleRepresentation {

	/**
	 * Max number of resources that the storage can contain
	 */
	private final int maxResources;

	public ClientMaxResourceNumberRuleRepresentation(
		@JsonProperty("maxResources") int maxResources
	) {
		this.maxResources = maxResources;
	}

	public int getMaxResources() {
		return maxResources;
	}
}
