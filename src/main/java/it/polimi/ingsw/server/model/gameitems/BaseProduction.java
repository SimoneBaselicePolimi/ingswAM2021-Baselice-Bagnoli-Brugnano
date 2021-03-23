package it.polimi.ingsw.server.model.gameitems;


import java.util.Map;

public class BaseProduction implements Production {

	public Map<ResourceType, Integer> getProductionCost() {
		return null;
	}

	public int getProductionStarResourceCost() {
		return 0;
	}

	public Map<ResourceType, Integer> getProductionResourceReward() {
		return null;
	}

	public int getProductionStarResourceReward() {
		return 0;
	}

	public int getProductionFaithReward() {
		return 0;
	}

	public boolean isProductionActive() {
		return false;
	}

}
