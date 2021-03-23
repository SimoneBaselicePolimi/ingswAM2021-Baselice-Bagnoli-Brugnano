package it.polimi.ingsw.model.gameitems;

import it.polimi.ingsw.model.gameitems.leadercard.Map_ResourceType, Integer_;

public interface Production {

	public abstract Map_ResourceType, Integer_ getProductionCost();

	public abstract int getProductionStarResourceCost();

	public abstract Map_ResourceType, Integer_ getProductionResourceReward();

	public abstract int getProductionStarResourceReward();

	public abstract int getProductionFaithReward();

	public abstract boolean isProductionActive();

}
