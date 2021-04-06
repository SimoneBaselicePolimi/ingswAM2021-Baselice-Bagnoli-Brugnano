package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.*;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.List;

public class LeaderCard {
	private LeaderCardState state;
	private LeaderCardRequirement requirement;
	private List<Production> production;
	private List<ResourceStorage> resourceStorage;
	private List<DevelopmentCardCostDiscount> cardCostDiscount;
	private List<WhiteMarbleSubstitution> whiteMarbleSubstitutions;
	int victoryPoints;

	/**
	 * LeaderCard constructor
	 * @param requirement requirement to activate the leader card
	 * @param production list of productions (special skill) that the leader card can own (it can be an empty list)
	 * @param resourceStorage list of resource storages (special skill) that the leader card can own (it can be an empty list)
	 * @param cardCostDiscount list of discounts (special skill) that the leader card can own (it can be an empty list)
	 * @param whiteMarbleSubstitutions list of substitutions with white marbles (special skill) that the leader card can own
	 *                                  (it can be an empty list)
	 * @param victoryPoints number of victory points that the card gives
	 */
	public LeaderCard (LeaderCardRequirement requirement,
					   List<Production> production,
					   List<ResourceStorage> resourceStorage,
					   List<DevelopmentCardCostDiscount> cardCostDiscount,
					   List<WhiteMarbleSubstitution> whiteMarbleSubstitutions,
					   int victoryPoints){
		this.requirement = requirement;
		this.state = LeaderCardState.HIDDEN;
		this.production=production;
		this.resourceStorage=resourceStorage;
		this.cardCostDiscount=cardCostDiscount;
		this.whiteMarbleSubstitutions=whiteMarbleSubstitutions;
		this.victoryPoints=victoryPoints;
	}

	/**
	 * the method verifies that the player has the necessary requirements to activate the card
	 * @param playerContext reference to the single player
	 * @return true if the player satisfies requirements of the leader card
	 */
	public boolean areRequirementsSatisfied(PlayerContext playerContext) {
		return (requirement.checkRequirement(playerContext));
	}

	/**
	 * the method changes the state of the leader card by activating it (the player can use it)
	 * @param playerContext
	 * @throws LeaderCardRequirementsNotSatisfiedException if the player doesn't satisfy requirements of the leader card
	 */
	public void activateLeaderCard(PlayerContext playerContext) throws LeaderCardRequirementsNotSatisfiedException {
		if(!areRequirementsSatisfied(playerContext))
			throw new LeaderCardRequirementsNotSatisfiedException();
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
	 * @return the state of the leader card: ACTIVE, DISCARDED or HIDDEN
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
	 * @return list of resource storages (special skill) that the leader card can own (it can be an empty list)
	 */
	public List<ResourceStorage> getResourceStorages() {
		return resourceStorage;
	}

	/**
	 * @return list of discounts (special skill) that the leader card can own (it can be an empty list)
	 */
	public List<DevelopmentCardCostDiscount> getDevelopmentCardCostDiscount() {
		return cardCostDiscount;
	}

	/**
	 * @return list of substitutions with white marbles (special skill) that the leader card can own (it can be an empty list)
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
