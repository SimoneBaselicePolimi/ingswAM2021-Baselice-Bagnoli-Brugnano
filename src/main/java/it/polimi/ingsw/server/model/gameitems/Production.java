package it.polimi.ingsw.server.model.gameitems;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerProductionRepresentation;

import java.util.Map;

/**
 * This class represents the Production power held by different types of Game Items (Leader Cards, Development Cards).
 * A generic Production Item is characterized by a cost (made of particular Resources or generic Resources) and
 * a reward (made of particular Resources, generic Resources or Faith Points): the Player who owns this Production Item
 * can pay this cost to obtain this reward.
 * Each Production Item is identified by an unique ID.
 */
public class Production extends RegisteredIdentifiableItem implements Representable<ServerProductionRepresentation> {
	/**
	 * Production cost made of specific type and number of Resources
	 */
	private final Map<ResourceType, Integer> resourceCost;

	/**
	 * Production reward made of specific type and number of Resources
	 */
	private final Map<ResourceType, Integer> resourceReward;

	/**
	 * Production cost made of a generic type of Resource (Player can choose), in a fixed quantity
	 */
	private final int starResourceCost;

	/**
	 * Production reward made of a generic type of Resource (Player can choose), in a fixed quantity
	 */
	private final int starResourceReward;

	/**
	 * Production reward made of a fixed number of Faith Points
	 */
	private final int faithReward;

	/**
	 * Class constructor.
	 * @param productionID ID which identifies this specific Production Item
	 * @param gameItemsManager a reference to gameItemsManager is needed to register the new Production object
	 *                          (see {@link RegisteredIdentifiableItem})
	 * @param resourceCost cost made of specific type and number of Resources
	 * @param resourceReward reward made of specific type and number of Resources
	 * @param starResourceCost cost made of a generic type of Resource, in a fixed quantity
	 * @param starResourceReward reward made of a generic type of Resource, in a fixed quantity
	 * @param faithReward reward made of a fixed number of Faith Points
	 * @throws IllegalArgumentException if a Map with negative values or negative numbers (cost and reward)
	 * are passed as parameters
	 */
	public Production(String productionID, GameItemsManager gameItemsManager, Map<ResourceType, Integer> resourceCost,
					  Map<ResourceType, Integer> resourceReward, int starResourceCost, int starResourceReward, int faithReward)
			throws IllegalArgumentException {
		super(productionID, gameItemsManager);
		if(resourceCost.values().stream().anyMatch(v -> v<0) || resourceReward.values().stream().anyMatch(v -> v<0)
		|| starResourceCost<0 || starResourceReward<0 || faithReward<0 || productionID == null)
			throw new IllegalArgumentException();
		this.resourceCost = resourceCost;
		this.resourceReward = resourceReward;
		this.starResourceCost = starResourceCost;
		this.starResourceReward = starResourceReward;
		this.faithReward = faithReward;
	}

	/**
	 * Method to get the Production cost made of specific type and number of Resources.
	 * @return Map of Resources and quantity needed
	 */
	public Map<ResourceType, Integer> getProductionResourceCost() {
		return resourceCost;
	}

	/**
	 * Method to get the Production reward made of specific type and number of Resources.
	 * @return Map of Resources and quantity given
	 */
	public Map<ResourceType, Integer> getProductionResourceReward() {
		return resourceReward;
	}

	/**
	 * Method to get the Production cost made of generic type of Resources.
	 * @return number of generic Resources needed
	 */
	public int getProductionStarResourceCost() {
		return starResourceCost;
	}

	/**
	 * Method to get the Production reward made of generic type of Resources.
	 * @return number of generic Resources given
	 */
	public int getProductionStarResourceReward() {
		return starResourceReward;
	}

	/**
	 * Method to get the reward made of Faith Points.
	 * @return number of Faith Points given
	 */
	public int getProductionFaithReward() {
		return faithReward;
	}

	@Override
	public ServerProductionRepresentation getServerRepresentation() {
		return new ServerProductionRepresentation(
			itemID,
			resourceCost,
			resourceReward,
			starResourceCost,
			starResourceReward,
			faithReward
		);
	}

	@Override
	public ServerProductionRepresentation getServerRepresentationForPlayer(Player player) {
		return getServerRepresentation();
	}
}
