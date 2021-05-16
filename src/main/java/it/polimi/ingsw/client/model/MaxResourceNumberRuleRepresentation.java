package it.polimi.ingsw.client.model;

public class MaxResourceNumberRuleRepresentation extends ResourceStorageRuleRepresentation {
	/**
	 * Max number of resources that the storage can contain
	 */
	private final int maxResources;

	public MaxResourceNumberRuleRepresentation(int maxResources) {
		this.maxResources = maxResources;
	}

	public int getMaxResources() {
		return maxResources;
	}
}
