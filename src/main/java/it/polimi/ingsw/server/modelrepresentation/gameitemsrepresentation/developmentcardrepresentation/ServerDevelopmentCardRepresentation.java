package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerProductionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerRegisteredIdentifiableItemRepresentation;

import java.util.Map;

public class ServerDevelopmentCardRepresentation extends ServerRegisteredIdentifiableItemRepresentation {

    public final DevelopmentCardLevel level;
    public final DevelopmentCardColour colour;
    public final ServerProductionRepresentation production;
    public final int victoryPoints;
    public final Map<ResourceType, Integer> purchaseCost;

    /**
     * DevelopmentCardRepresentation constructor
     * @param itemID ID of the development card, see {@link IdentifiableItem}
     * @param level of the development card
     * @param colour of the development card
     * @param production that the the development card can give to the player
     * @param victoryPoints that the development card gives at the end of the game
     * @param purchaseCost necessary to buy the development card
     */
    public ServerDevelopmentCardRepresentation(
        @JsonProperty("itemID") String itemID,
        @JsonProperty("level") DevelopmentCardLevel level,
        @JsonProperty("colour") DevelopmentCardColour colour,
        @JsonProperty("production") ServerProductionRepresentation production,
        @JsonProperty("victoryPoints") int victoryPoints,
        @JsonProperty("purchaseCost") Map<ResourceType, Integer> purchaseCost
    ) {
        super(itemID);
        this.level = level;
        this.colour = colour;
        this.production = production;
        this.victoryPoints = victoryPoints;
        this.purchaseCost = purchaseCost;
    }

}
