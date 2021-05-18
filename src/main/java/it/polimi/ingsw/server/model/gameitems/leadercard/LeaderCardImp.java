package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.*;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerLeaderCardRepresentation;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class represent a specific type of card used in the game: the leader card
 */
public class LeaderCardImp extends RegisteredIdentifiableItem implements LeaderCard {

	private LeaderCardState state;
	private final Set<LeaderCardRequirement> requirements;
	private final Set<Production> productions;
	private final Set<ResourceStorage> resourceStorages;
	private final Set<DevelopmentCardCostDiscount> cardCostDiscounts;
	private final Set<WhiteMarbleSubstitution> whiteMarbleSubstitutions;
	private final int victoryPoints;

	/**
	 * LeaderCard constructor
	 * @param leaderCardID ID of the leader card, see {@link IdentifiableItem}
	 * @param gameItemsManager a reference to gameItemsManager is needed to register the new LeaderCard object
	 *                         (see {@link RegisteredIdentifiableItem})
	 * @param requirements requirements to activate the leader card
	 * @param productions set of productions (special skill) that the leader card can own (it can be an empty set)
	 * @param resourceStorages set of resource storages (special skill) that the leader card can own (it can be an empty set)
	 * @param cardCostDiscounts set of discounts (special skill) that the leader card can own (it can be an empty set)
	 * @param whiteMarbleSubstitutions set of substitutions with white marbles (special skill) that the leader card can own
	 * (it can be an empty set)
	 * @param victoryPoints number of victory points that the card gives
	 */
	public LeaderCardImp(
		String leaderCardID,
		GameItemsManager gameItemsManager,
		Set<LeaderCardRequirement> requirements,
		Set<Production> productions,
		Set<ResourceStorage> resourceStorages,
		Set<DevelopmentCardCostDiscount> cardCostDiscounts,
		Set<WhiteMarbleSubstitution> whiteMarbleSubstitutions,
		int victoryPoints
	){
	    super(leaderCardID, gameItemsManager);
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
	@Override
	public boolean areRequirementsSatisfied(PlayerContext playerContext) {
		for (LeaderCardRequirement requirement : requirements) {
			if (!requirement.checkRequirement(playerContext))
				return false;
		}
		return true;
	}

	/**
	 * Method to change the state of the leader card from HIDDEN to ACTIVE
	 * (this method should be called when the player wants to activate the card)
	 * @param playerContext reference to the single player
	 * @throws LeaderCardRequirementsNotSatisfiedException if the leader card of the player
	 * doesn't satisfy some requirements
	 */
	@Override
	public void activateLeaderCard(PlayerContext playerContext) throws LeaderCardRequirementsNotSatisfiedException {
		if(!areRequirementsSatisfied(playerContext))
			throw new LeaderCardRequirementsNotSatisfiedException("Leader card activation denied: the card does not meet the required requirements");
		if (this.state != LeaderCardState.HIDDEN)
			throw new LeaderCardRequirementsNotSatisfiedException("Leader card activation denied: the card state must be HIDDEN");
		this.state = LeaderCardState.ACTIVE;
	}

	/**
	 * Method to change the state of the leader card from HIDDEN to DISCARDED
	 * (the player no longer has that leader card in his hand)
	 */
	@Override
	public void discardLeaderCard() throws LeaderCardRequirementsNotSatisfiedException{
		if (this.state != LeaderCardState.HIDDEN)
			throw new LeaderCardRequirementsNotSatisfiedException ("Leader card discard denied: the card state must be HIDDEN");
		this.state = LeaderCardState.DISCARDED;
	}

	/**
	 * Method to get the state of the leader card
	 * @return the state of the leader card: ACTIVE, DISCARDED or HIDDEN
	 */
	@Override
	public LeaderCardState getState() {
		return state;
	}

	/**
	 * Method to get the list of productions of the leader card
	 * @return list of productions (special skills) that the leader card can own (it can be an empty list)
	 */
	@Override
	public Set<Production> getProductions() {
		return productions;
	}

	/**
	 * Method to get the list of resource storages of the leader card
	 * @return list of resource storages (special skills) that the leader card can own (it can be an empty list)
	 */
	@Override
	public Set<ResourceStorage> getResourceStorages() {
		return resourceStorages;
	}

	/**
	 * Method to get the list of discounts of the leader card
	 * @return list of discounts (special skills) that the leader card can own (it can be an empty list)
	 */
	@Override
	public Set<DevelopmentCardCostDiscount> getDevelopmentCardCostDiscount() {
		return cardCostDiscounts;
	}

	/**
	 * Method to get the list of substitutions of the leader card
	 * @return list of substitutions with white marbles (special skills) that the leader card can own (it can be an empty list)
	 */
	@Override
	public Set<WhiteMarbleSubstitution> getWhiteMarbleSubstitutions() {
		return whiteMarbleSubstitutions;
	}

	/**
	 * Method to get the number of victory points of the leader card
	 * @return number of victory points that the card gives at the end of the game
	 */
	@Override
	public int getVictoryPoints() {
		return victoryPoints;
	}

	@Override
	public ServerLeaderCardRepresentation getServerRepresentation() {
		return new ServerLeaderCardRepresentation(
			getItemId(),
			getState(),
			requirements.stream().map(Representable::getServerRepresentation).collect(Collectors.toSet()),
			productions.stream().map(Production::getServerRepresentation).collect(Collectors.toSet()),
			resourceStorages.stream().map(ResourceStorage::getServerRepresentation).collect(Collectors.toSet()),
			cardCostDiscounts.stream().map(DevelopmentCardCostDiscount::getServerRepresentation).collect(Collectors.toSet()),
			whiteMarbleSubstitutions.stream().map(WhiteMarbleSubstitution::getServerRepresentation).collect(Collectors.toSet()),
			victoryPoints,
			false
		);
	}

	//TODO Informazioni private da passare a ciascun giocatore (persistenza)
	@Override
	public ServerLeaderCardRepresentation getServerRepresentationForPlayer(Player player) {
		return null;
	}
}
