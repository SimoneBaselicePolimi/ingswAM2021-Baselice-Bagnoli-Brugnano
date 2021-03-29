package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.*;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.List;
import java.util.Map;

public class LeaderCard {
	private LeaderCardState state;
	private LeaderCardRequirement requirement;

	/**
	 *
	 * @param playerContext reference to the single player
	 * @return true if the player has at least one card in his hand: this means that he meets
	 * the requirements and he can either discard the card or play it
	 */
	public boolean areRequirementsSatisfied(PlayerContext playerContext) {
		if(!requirement.checkRequirement(playerContext))
			return false;
		return true;
	}

	public void activateLeaderCard(PlayerContext playerContext) throws LeaderCardRequirementsNotSatisfied {
		if(!areRequirementsSatisfied(playerContext))
			throw new LeaderCardRequirementsNotSatisfied();
		this.state = LeaderCardState.ACTIVE;
	}

	public void discardLeaderCard() {
		this.state = LeaderCardState.DISCARDED;
	}

	public LeaderCardState getState() {
		return state;
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
