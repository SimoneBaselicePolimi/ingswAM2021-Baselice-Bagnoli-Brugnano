package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gameitems.*;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.List;
import java.util.Map;

public class LeaderCard implements WhiteMarbleSubstitution, LeaderStorage, ProductionDiscount, Production {

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

	public LeaderCardState getState() {
		return null;
	}

	public Map<ResourceType, Integer> getProductionDiscount() {
		return null;
	}

	public boolean isProductionDiscountActive() {
		return false;
	}

	public List<MarbleColour> getWhileMarbleSubstitution() {
		return null;
	}

	public boolean isWhiteMarbleSubstitutionActive() {
		return false;
	}

	public List<ResourceStorage> getLeaderStorage() {
		return null;
	}

	public boolean isLeaderStorageActive() {
		return false;
	}

}
