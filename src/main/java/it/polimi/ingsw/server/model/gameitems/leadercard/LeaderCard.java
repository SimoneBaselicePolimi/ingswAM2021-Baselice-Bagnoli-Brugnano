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
	private List<Production> production;
	private List<ResourceStorage> resourceStorage;
	private List<ProductionDiscount> productionDiscounts;
	private List<WhiteMarbleSubstitution> whiteMarbleSubstitutions;
	int victoryPoints;

	/**
	 * LeaderCard constructor
	 * @param requirement requirement to activate the leader card
	 * @param production list of productions (special skill) that the leader card can own (it can be an empty list)
	 * @param resourceStorage list of resource storage (special skill) that the leader card can own (it can be an empty list)
	 * @param productionDiscounts list of discounts (special skill) that the leader card can own (it can be an empty list)
	 * @param whiteMarbleSubstitutions list of substitution with white marble (special skill) that the leader card can own
	 *                                  (it can be an empty list)
	 * @param victoryPoints number of victory points that the card gives
	 */
	public LeaderCard (LeaderCardRequirement requirement,
					   List<Production> production,
					   List<ResourceStorage> resourceStorage,
					   List<ProductionDiscount> productionDiscounts,
					   List<WhiteMarbleSubstitution> whiteMarbleSubstitutions,
					   int victoryPoints){
		this.requirement = requirement;
		this.state = LeaderCardState.IN_HAND;
		this.production=production;
		this.resourceStorage=resourceStorage;
		this.productionDiscounts=productionDiscounts;
		this.whiteMarbleSubstitutions=whiteMarbleSubstitutions;
		this.victoryPoints=victoryPoints;
	}
	/**
	 * the method verifies that the player has the necessary requisites to activate the card
	 * @param playerContext reference to the single player
	 * @return true if the player satisfies requirments of the leader card
	 */
	public boolean areRequirementsSatisfied(PlayerContext playerContext) {
		if(!requirement.checkRequirement(playerContext))
			return false;
		return true;
	}

	/**
	 * the method changes the state of the leader card by activating it (the player can use it)
	 * @param playerContext
	 * @throws LeaderCardRequirementsNotSatisfied if the player doesn't satisfy requirments of the leader card
	 */
	public void activateLeaderCard(PlayerContext playerContext) throws LeaderCardRequirementsNotSatisfied {
		if(!areRequirementsSatisfied(playerContext))
			throw new LeaderCardRequirementsNotSatisfied();
		this.state = LeaderCardState.ACTIVE;
	}

	/**
	 * the method changes the state of the leader card by discarding it
	 * (the player no longer has that leader card in his hand)
	 */
	public void discardLeaderCard() {
		this.state = LeaderCardState.DISCARDED;
	}

	/**
	 * @return the state of the leader card
	 */
	public LeaderCardState getState() {
		return state;
	}

	/**
	 * @return list of productions (special skill) that the leader card can own (it can be an empty list)
	 */
	public List<Production> getProductions() {
		return production;
	}

	/**
	 * @return list of resource storage (special skill) that the leader card can own (it can be an empty list)
	 */
	public List<ResourceStorage> getResourceStorages() {
		return resourceStorage;
	}

	/**
	 * @return list of discounts (special skill) that the leader card can own (it can be an empty list)
	 */
	public List<ProductionDiscount> getProductionDiscounts() {
		return productionDiscounts;
	}

	/**
	 * @return list of substitution with white marble (special skill) that the leader card can own (it can be an empty list)
	 */
	public List<WhiteMarbleSubstitution> getWhiteMarbleSubstitutions() {
		return whiteMarbleSubstitutions;
	}

	/**
	 * @return number of victory points that the card gives
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}

}
