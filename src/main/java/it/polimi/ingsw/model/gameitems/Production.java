package it.polimi.ingsw.model.gameitems;


import java.util.Map;

public interface Production {

	Map<ResourceType, Integer> getProductionCost();

	int getProductionStarResourceCost();

	Map<ResourceType, Integer> getProductionResourceReward();

	int getProductionStarResourceReward();

	int getProductionFaithReward();

	boolean isProductionActive();

}
