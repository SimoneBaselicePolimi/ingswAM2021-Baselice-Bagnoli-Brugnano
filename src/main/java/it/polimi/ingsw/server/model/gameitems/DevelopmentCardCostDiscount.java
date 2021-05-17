package it.polimi.ingsw.server.model.gameitems;

/**
 * This class represents the Development Card Cost Discount power held by some Game Items (Leader Cards).
 * A Player who owns this Discount Item, when buying a Development Card, can pay its cost with a discount
 * of the indicated Resource (if the card he's buying has that Resource type as a cost).
*/
public class DevelopmentCardCostDiscount extends RegisteredIdentifiableItem{
	/**
	 * Resource type the Player can discount from the cost of a Development Card
	 */
	private final ResourceType resourceType;

	/**
	 * Number of resources the Player can discount from the cost of a Development Card
	 */
	private final int amountToDiscount;

	/**
	 * Class constructor.
	 * @param costDiscountID ID which identifies this specific Development Card Cost Discount Item
	 * @param gameItemsManager a reference to gameItemsManager is needed to register the new DevelopmentCardCostDiscount object
	 *                          (see {@link RegisteredIdentifiableItem})
	 * @param resourceType resource the Player can discount from the cost
	 * @param amountToDiscount number of resources the Player can discount from the cost
	 * @throws IllegalArgumentException if null Resource type or a negative number are passed as parameters
	 */
	public DevelopmentCardCostDiscount(
		String costDiscountID, GameItemsManager gameItemsManager, ResourceType resourceType, int amountToDiscount
	) throws IllegalArgumentException {
		super(costDiscountID, gameItemsManager);
		if(resourceType == null || amountToDiscount<0)
			throw new IllegalArgumentException();
		this.resourceType = resourceType;
		this.amountToDiscount = amountToDiscount;
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
	public int getAmountToDiscount() {
		return amountToDiscount;
	}

}
