package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class DevelopmentCard implements Production {

	public DevelopmentCardLevel getLevel() {
		return null;
	}

	public MarbleColour getColour() {
		return null;
	}

	public Map<ResourceType, Integer> getPrice() {
		return null;
	}

	public Map<ResourceType, Integer> getProductionResourceCost() {
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
