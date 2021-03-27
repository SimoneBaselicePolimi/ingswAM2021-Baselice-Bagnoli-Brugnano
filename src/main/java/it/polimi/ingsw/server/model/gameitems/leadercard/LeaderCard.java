package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.*;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.List;
import java.util.Map;

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

	public List<ProductionDiscount> getProductionDiscounts() {
		return null;
	}

	public List<WhiteMarbleSubstitution> getWhiteMarbleSubstitutions() {
		return null;
	}

	public int getVictoryPoints() {
		return 0;
	}

}
