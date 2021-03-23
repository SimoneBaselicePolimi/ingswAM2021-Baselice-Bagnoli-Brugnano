package it.polimi.ingsw.model.gameitems.developmentcard;

import it.polimi.ingsw.model.gameitems.Production;
import it.polimi.ingsw.model.gameitems.leadercard.Map_ResourceType, Integer_;

public class DevelopmentCard implements Production {

	public int getLevel() {
		return 0;
	}

	public int getColour() {
		return 0;
	}

	public Map_ResourceType, Integer_ getPrice() {
		return null;
	}

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

	public int getVictoryPoints() {
		return 0;
	}

}
