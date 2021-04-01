package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.*;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.List;

public class LeaderCard {

	public boolean areRequirementsSatisfied(PlayerContext playerContext) {
		return false;
	}

	public void activateLeaderCard() throws LeaderCardRequirementsNotSatisfied {

	}

	public void discardLeaderCard() {

	}

	public LeaderCardState getState() {
		return null;
	}

	public List<Production> getProductions() {
		return null;
	}

	public List<ResourceStorage> getResourceStorages() {
		return null;
	}

	public List<DevelopmentCardCostDiscount> getProductionDiscounts() {
		return null;
	}

	public List<WhiteMarbleSubstitution> getWhiteMarbleSubstitutions() {
		return null;
	}

	public int getVictoryPoints() {
		return 0;
	}

}
