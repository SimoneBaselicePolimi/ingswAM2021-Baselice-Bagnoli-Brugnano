package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientRegisteredIdentifiableItemRepresentation;
import it.polimi.ingsw.server.model.gameitems.*;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.Map;

public class ClientDevelopmentCardRepresentation extends ClientRegisteredIdentifiableItemRepresentation {

    private DevelopmentCardLevel level;
    private DevelopmentCardColour colour;
    private ClientProductionRepresentation production;
    private int victoryPoints;
    private Map<ResourceType, Integer> purchaseCost;

    /**
     * DevelopmentCardRepresentation constructor
     * @param itemID ID of the development card, see {@link IdentifiableItem}
     * @param gameItemsManager a reference to gameItemsManager is needed to register the new DevelopmentCard object
     * (see {@link RegisteredIdentifiableItem})
     * @param level of the development card
     * @param colour of the development card
     * @param production that the the development card can give to the player
     * @param victoryPoints that the development card gives at the end of the game
     * @param purchaseCost necessary to buy the development card
     */
    public ClientDevelopmentCardRepresentation(
        @JsonProperty("itemID") String itemID,
        @JacksonInject("gameItemsManager") GameItemsManager gameItemsManager,
        @JsonProperty("level") DevelopmentCardLevel level,
        @JsonProperty("colour") DevelopmentCardColour colour,
        @JsonProperty("production") ClientProductionRepresentation production,
        @JsonProperty("victoryPoints") int victoryPoints,
        @JsonProperty("purchaseCost") Map<ResourceType, Integer> purchaseCost
    ) {
        super(itemID, gameItemsManager);
        this.level = level;
        this.colour = colour;
        this.production = production;
        this.victoryPoints = victoryPoints;
        this.purchaseCost = purchaseCost;
    }

    public DevelopmentCardLevel getLevel() {
        return level;
    }

    public DevelopmentCardColour getColour() {
        return colour;
    }

    public ClientProductionRepresentation getProduction() {
        return production;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Map<ResourceType, Integer> getPurchaseCost() {
        return purchaseCost;
    }
}
