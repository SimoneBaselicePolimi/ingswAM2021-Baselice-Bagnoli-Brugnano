package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.*;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.List;

/**
 * This class represent a specific type of cards used in the game: the leader cards
 */
public class LeaderCard {
	private LeaderCardState state;
	private List<LeaderCardRequirement> requirements;
	private List<Production> productions;
	private List<ResourceStorage> resourceStorages;
	private List<DevelopmentCardCostDiscount> cardCostDiscounts;
	private List<WhiteMarbleSubstitution> whiteMarbleSubstitutions;
	int victoryPoints;


	/**
	 * LeaderCard constructor
	 * @param requirements requirements to activate the leader card
	 * @param productions list of productions (special skill) that the leader card can own (it can be an empty list)
	 * @param resourceStorages list of resource storages (special skill) that the leader card can own (it can be an empty list)
	 * @param cardCostDiscounts list of discounts (special skill) that the leader card can own (it can be an empty list)
	 * @param whiteMarbleSubstitutions list of substitutions with white marbles (special skill) that the leader card can own
	 * (it can be an empty list)
	 * @param victoryPoints number of victory points that the card gives
	 */
	public LeaderCard (List<LeaderCardRequirement> requirements,
					   List<Production> productions,
					   List<ResourceStorage> resourceStorages,
					   List<DevelopmentCardCostDiscount> cardCostDiscounts,
					   List<WhiteMarbleSubstitution> whiteMarbleSubstitutions,
					   int victoryPoints){
		this.requirements = requirements;
		this.state = LeaderCardState.HIDDEN;
		this.productions = productions;
		this.resourceStorages = resourceStorages;
		this.cardCostDiscounts = cardCostDiscounts;
		this.whiteMarbleSubstitutions=whiteMarbleSubstitutions;
		this.victoryPoints=victoryPoints;
	}

	/**
	 * Method to verify that the player has the necessary requirements to activate the leader card
	 * @param playerContext reference to the single player
	 * @return true if the player satisfies all requirements of the leader card
	 */
	public boolean areRequirementsSatisfied(PlayerContext playerContext) {
		for (LeaderCardRequirement requirement : requirements) {
			if (!requirement.checkRequirement(playerContext))
				return false;
		}
		return true;
	}

	/**
	 * Method to change the state of the leader card by activating it (the player can use it)
	 * @param playerContext reference to the single player
	 * @throws LeaderCardRequirementsNotSatisfiedException if the leader card of the player
	 * doesn't satisfy some requirements
	 */
	public void activateLeaderCard(PlayerContext playerContext) throws LeaderCardRequirementsNotSatisfiedException {
		if(!areRequirementsSatisfied(playerContext))
			throw new LeaderCardRequirementsNotSatisfiedException();
		this.state = LeaderCardState.ACTIVE;
	}

	/**
	 * Method to change the state of the leader card by discarding it
	 * (the player no longer has that leader card in his hand)
	 */
	public void discardLeaderCard() {
		this.state = LeaderCardState.DISCARDED;
	}

	/**
	 * Method to get the state of the leader card
	 * @return the state of the leader card: ACTIVE, DISCARDED or HIDDEN
	 */
	public LeaderCardState getState() {
		return state;
	}

	/**
	 * Method to get the list of productions of the leader card
	 * @return list of productions (special skills) that the leader card can own (it can be an empty list)
	 */
	public List<Production> getProductions() {
		return productions;
	}

	/**
	 * Method to get the list of resource storages of the leader card
	 * @return list of resource storages (special skills) that the leader card can own (it can be an empty list)
	 */
	public List<ResourceStorage> getResourceStorages() {
		return resourceStorages;
	}

	/**
	 * Method to get the list of discounts of the leader card
	 * @return list of discounts (special skills) that the leader card can own (it can be an empty list)
	 */
	public List<DevelopmentCardCostDiscount> getDevelopmentCardCostDiscount() {
		return cardCostDiscounts;
	}

	/**
	 * Method to get the list of substitutions of the leader card
	 * @return list of substitutions with white marbles (special skills) that the leader card can own (it can be an empty list)
	 */
	public List<WhiteMarbleSubstitution> getWhiteMarbleSubstitutions() {
		return whiteMarbleSubstitutions;
	}

	/**
	 * Method to get the number of victory points of the leader card
	 * @return number of victory points that the card gives at the end of the game
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}

}
