package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
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
     * @param gameItemsManager a reference to gameItemsManager is needed to register the new DevelopmentCard object
     * (see {@link RegisteredIdentifiableItem})
     * @param level of the development card
     * @param colour of the development card
     * @param production that the the development card can give to the player
     * @param victoryPoints that the development card gives at the end of the game
     * @param purchaseCost necessary to buy the development card
     */
    public ServerDevelopmentCardRepresentation(
        String itemID,
        GameItemsManager gameItemsManager,
        DevelopmentCardLevel level,
        DevelopmentCardColour colour,
        ServerProductionRepresentation production,
        int victoryPoints,
        Map<ResourceType, Integer> purchaseCost
    ) {
        super(itemID, gameItemsManager);
        this.level = level;
        this.colour = colour;
        this.production = production;
        this.victoryPoints = victoryPoints;
        this.purchaseCost = purchaseCost;
    }

}
