package it.polimi.ingsw.server.model.gameitems;

/**
 * This class represents the Production Discount power held by some Game Items (Leader Cards).
 * A Player who owns this Production Discount Item, when buying a Development Card, can pay its cost
 * with a discount of the indicated Resource (if the card he's buying has that Resource type as a cost).
*/
public class ProductionDiscount {
	/**
	 * Resource type the Player can discount from the cost of a Development Card
	 */
	private final ResourceType resourceType;

	/**
	 * Number of resources the Player can discount from the cost of a Development Card
	 */
	private final int getDiscount;

	/**
	 * Class constructor.
	 * @param resourceType resource the Player can discount from the cost
	 * @param getDiscount number of resources the Player can discount from the cost
	 * @throws IllegalArgumentException if null Resource type or a negative number are passed as parameters
	 */
	public ProductionDiscount(ResourceType resourceType, int getDiscount) throws IllegalArgumentException {
		if(resourceType == null || getDiscount<0)
			throw new IllegalArgumentException();
		this.resourceType = resourceType;
		this.getDiscount = getDiscount;
	}

	/**
	 * Method to get the type of resources the Player can discount from the cost of a Development Card.
	 * @return type of resources discounted
	 */
	public ResourceType getResourceTypeToDiscount() {
		return resourceType;
	}

	/**
	 * Method to get the number of resources the Player can discount from the cost of a Development Card.
	 * @return number of resources discounted
	 */
	public int getDiscount() {
		return getDiscount;
	}

}
