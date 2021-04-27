package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.*;

import java.util.Map;

/**
 * This class represent a specific type of cards used in the game: the development cards
 */
public class DevelopmentCard extends RegisteredIdentifiableItem {

	private DevelopmentCardLevel level;
	private DevelopmentCardColour colour;
	private Production production;
	private int victoryPoints;
	private Map<ResourceType, Integer> purchaseCost;

	/**
	 * DevelopmentCard constructor
	 * @param developmentCardID ID of the development card, see {@link IdentifiableItem}
     * @param gameItemsManager a reference to gameItemsManager is needed to register the new LeaderCard object
	 *                         (see {@link RegisteredIdentifiableItem})
	 * @param level of the development card
	 * @param colour of the development card
	 * @param production that the the development card can give to the player
	 * @param victoryPoints that the development card gives at the end of the game
	 * @param purchaseCost necessary to buy the development card
	 */
	public DevelopmentCard(
		String developmentCardID,
		GameItemsManager gameItemsManager,
		DevelopmentCardLevel level,
		DevelopmentCardColour colour,
		Production production,
		int victoryPoints,
		Map<ResourceType, Integer> purchaseCost
	){
	    super(developmentCardID, gameItemsManager);
		this.level=level;
		this.colour=colour;
		this.production=production;
		this.victoryPoints=victoryPoints;
		this.purchaseCost=purchaseCost;
	}

	/**
	 * Method to get the level of the development card
	 * @return level
	 */
	public DevelopmentCardLevel getLevel() {
		return level;
	}

	/**
	 * Method to get the colour of the development card
	 * @return colour
	 */
	public DevelopmentCardColour getColour() {
		return colour;
	}

	/**
	 * Method to get the production of the development card
	 * @return production
	 */
	public Production getProduction() {
		return production;
	}

	/**
	 * Method to get points that the development card gives (they are used at the end of the game)
	 * @return Victory points
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}

	/**
	 * Method to get the cost (resources) necessary to pay, in order to have the card
	 * @return Map<ResourceType, Integer> type and number of resources that represent the price to pay
	 */
	public  Map<ResourceType, Integer> getPurchaseCost(){
		return purchaseCost;
	}

}
