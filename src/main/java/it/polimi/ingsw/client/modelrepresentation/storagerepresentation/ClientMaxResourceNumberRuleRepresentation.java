package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

public class ClientMaxResourceNumberRuleRepresentation extends ClientResourceStorageRuleRepresentation {
	/**
	 * Max number of resources that the storage can contain
	 */
	private final int maxResources;

	public ClientMaxResourceNumberRuleRepresentation(int maxResources) {
		this.maxResources = maxResources;
	}

	public int getMaxResources() {
		return maxResources;
	}
}
