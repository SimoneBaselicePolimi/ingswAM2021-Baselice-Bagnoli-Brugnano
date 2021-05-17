package it.polimi.ingsw.server.modelrepresentation.storagerepresentation;

public class ServerMaxResourceNumberRuleRepresentation extends ServerResourceStorageRuleRepresentation {
	/**
	 * Max number of resources that the storage can contain
	 */
	private final int maxResources;

	public ServerMaxResourceNumberRuleRepresentation(int maxResources) {
		this.maxResources = maxResources;
	}

	public int getMaxResources() {
		return maxResources;
	}
}
