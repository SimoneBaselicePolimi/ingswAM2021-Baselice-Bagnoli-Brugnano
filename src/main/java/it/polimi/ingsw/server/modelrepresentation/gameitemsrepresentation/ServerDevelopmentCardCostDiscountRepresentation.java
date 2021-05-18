package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

public class ServerDevelopmentCardCostDiscountRepresentation extends ServerRegisteredIdentifiableItemRepresentation {

    /**
     * Resource type the Player can discount from the cost of a Development Card
     */
    public final ResourceType resourceType;

    /**
     * Number of resources the Player can discount from the cost of a Development Card
     */
    public final int amountToDiscount;

    /**
     * DevelopmentCardCostDiscountRepresentation constructor.
     * @param itemID
     * @param resourceType resource the Player can discount from the cost
     * @param amountToDiscount number of resources the Player can discount from the cost
     */
    public ServerDevelopmentCardCostDiscountRepresentation(
        String itemID,
        GameItemsManager gameItemsManager,
        ResourceType resourceType,
        int amountToDiscount
    ) {
        super(itemID);
        this.resourceType = resourceType;
        this.amountToDiscount = amountToDiscount;
    }
}
