package it.polimi.ingsw.model.gameitems;

import it.polimi.ingsw.model.gameitems.leadercard.Map_ResourceType, Integer_;

public class BaseProduction implements Production {

	public Map_ResourceType, Integer_ getProductionCost() {
		return null;
	}

	public int getProductionStarResourceCost() {
		return 0;
	}

	public Map_ResourceType, Integer_ getProductionResourceReward() {
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
