package it.polimi.ingsw.server.model.gameitems;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represents the Production power held by different types of Game Items (Leader Cards, Development Cards).
 * A generic Production Item is characterized by a cost (made of particular Resources or generic Resources) and
 * a reward (made of particular Resources, generic Resources or Faith Points): the Player who owns this Production Item
 * can pay this cost to obtain this reward.
 * Each Production Item is identified by an unique ID.
 */
public class Production {
	/**
	 * Production cost made of specific type and number of Resources
	 */
	private Map<ResourceType, Integer> resourceCost;

	/**
	 * Production reward made of specific type and number of Resources
	 */
	private Map<ResourceType, Integer> resourceReward;

	/**
	 * Production cost made of a generic type of Resource (Player can choose), in a fixed quantity
	 */
	private int starResourceCost;

	/**
	 * Production reward made of a generic type of Resource (Player can choose), in a fixed quantity
	 */
	private int starResourceReward;

	/**
	 * Production reward made of a fixed number of Faith Points
	 */
	private int faithReward;

	/**
	 * ID which identifies this specific Production Item
	 */
	private String productionID;

	/**
	 * Class constructor.
	 * @param resourceCost cost made of specific type and number of Resources
	 * @param resourceReward reward made of specific type and number of Resources
	 * @param starResourceCost cost made of a generic type of Resource, in a fixed quantity
	 * @param starResourceReward reward made of a generic type of Resource, in a fixed quantity
	 * @param faithReward reward made of a fixed number of Faith Points
	 * @param productionID ID which identifies this specific Production Item
	 */
	public Production(Map<ResourceType, Integer> resourceCost, Map<ResourceType, Integer> resourceReward,
					  int starResourceCost, int starResourceReward, int faithReward, String productionID) {
		this.resourceCost = resourceCost;
		this.resourceReward = resourceReward;
		this.starResourceCost = starResourceCost;
		this.starResourceReward = starResourceReward;
		this.faithReward = faithReward;
		this.productionID = productionID;
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

	/**
	 * Override of the equals method used to compare the equality between two Production Items.
	 * @param o Object to compare to this Production Item
	 * @return true if the Object passed as parameter is identified by the same Production ID of the Item
	 * which invokes this method, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Production)) return false;
		Production m = (Production) o;
		return (productionID.equals(m.productionID));
	}

	/**
	 * Override of the hashCode method used to return the hash code of this Production Item.
	 * @return hash code of this Production Item based on its ID
	 */
	@Override
	public int hashCode() {
		return productionID.hashCode();
	}

}
