package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
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
        @JsonProperty("itemID") String itemID,
        @JacksonInject("gameItemsManager") GameItemsManager gameItemsManager,
        @JsonProperty("resourceType") ResourceType resourceType,
        @JsonProperty("amountToDiscount") int amountToDiscount
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

    public String getDescription() {
        return Localization.getLocalizationInstance().getString(
            "leaderCards.specialPowers.developmentCardCostDiscount",
            amountToDiscount,
            amountToDiscount == 1 ? resourceType.getLocalizedNameSingular()
                : resourceType.getLocalizedNamePlural()
            );
    }

}
