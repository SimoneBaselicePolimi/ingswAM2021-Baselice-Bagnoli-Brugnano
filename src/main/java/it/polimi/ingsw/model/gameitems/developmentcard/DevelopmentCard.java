package it.polimi.ingsw.model.gameitems.developmentcard;

import it.polimi.ingsw.model.gameitems.Production;
import it.polimi.ingsw.model.gameitems.ResourceType;

import java.util.Map;

public class DevelopmentCard implements Production {

	public int getLevel() {
		return 0;
	}

	public int getColour() {
		return 0;
	}

	public Map<ResourceType, Integer> getPrice() {
		return null;
	}

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

	public int getVictoryPoints() {
		return 0;
	}

}
