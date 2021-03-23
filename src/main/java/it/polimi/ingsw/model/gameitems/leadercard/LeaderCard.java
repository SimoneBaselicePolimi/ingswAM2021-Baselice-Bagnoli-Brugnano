package it.polimi.ingsw.model.gameitems.leadercard;

import it.polimi.ingsw.model.gameitems.WhiteMarbleSubstitution;
import it.polimi.ingsw.model.gameitems.LeaderStorage;
import it.polimi.ingsw.model.gameitems.ProductionDiscount;
import it.polimi.ingsw.model.gameitems.Production;
import it.polimi.ingsw.model.gamecontext.playercontext.Map_ResourceType,Integer_;

public class LeaderCard implements WhiteMarbleSubstitution, LeaderStorage, ProductionDiscount, Production {

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

	public LeaderCardState getState() {
		return null;
	}

	public Map_ResourceType,Integer_ getProductionDiscount() {
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


	/**
	 * @see ProductionDiscount#getProductionDiscount()
	 */
	public Map<ResourceType,int> getProductionDiscount() {
		return null;
	}

}
