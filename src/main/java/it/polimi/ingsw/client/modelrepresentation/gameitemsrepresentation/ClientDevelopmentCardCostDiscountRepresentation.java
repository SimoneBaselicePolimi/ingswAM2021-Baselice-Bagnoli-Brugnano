package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

public class ClientDevelopmentCardCostDiscountRepresentation extends ClientRegisteredIdentifiableItemRepresentation {

    /**
     * Resource type the Player can discount from the cost of a Development Card
     */
    private final ResourceType resourceType;

    /**
     * Number of resources the Player can discount from the cost of a Development Card
     */
    private final int amountToDiscount;

    /**
     * DevelopmentCardCostDiscountRepresentation constructor.
     * @param itemID
     * @param gameItemsManager
     * @param resourceType resource the Player can discount from the cost
     * @param amountToDiscount number of resources the Player can discount from the cost
     */
    public ClientDevelopmentCardCostDiscountRepresentation(
        String itemID,
        GameItemsManager gameItemsManager,
        ResourceType resourceType,
        int amountToDiscount
    ) {
        super(itemID, gameItemsManager);
        this.resourceType = resourceType;
        this.amountToDiscount = amountToDiscount;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getAmountToDiscount() {
        return amountToDiscount;
    }
}
