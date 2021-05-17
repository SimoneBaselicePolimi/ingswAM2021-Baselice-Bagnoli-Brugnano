package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation;

import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerDevelopmentCardCostDiscountRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerProductionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerRegisteredIdentifiableItemRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerWhiteMarbleSubstitutionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.storagerepresentation.ServerResourceStorageRepresentation;

import java.util.List;

public class ServerLeaderCardRepresentation extends ServerRegisteredIdentifiableItemRepresentation {

    public final LeaderCardState state;
    public final List<ServerLeaderCardRequirementRepresentation> requirements;
    public final List<ServerProductionRepresentation> productions;
    public final List<ServerResourceStorageRepresentation> resourceStorages;
    public final List<ServerDevelopmentCardCostDiscountRepresentation> cardCostDiscounts;
    public final List<ServerWhiteMarbleSubstitutionRepresentation> whiteMarbleSubstitutions;
    public final int victoryPoints;
    public final boolean canBeActivated;

    /**
     * LeaderCardRepresentation constructor
     * @param itemID ID of the leader card, see {@link IdentifiableItem}
     * @param gameItemsManager a reference to gameItemsManager is needed to register the new LeaderCard object
     * (see {@link RegisteredIdentifiableItem})
     * @param requirements requirements to activate the leader card
     * @param productions list of productions (special skill) that the leader card can own (it can be an empty list)
     * @param resourceStorages list of resource storages (special skill) that the leader card can own (it can be an empty list)
     * @param cardCostDiscounts list of discounts (special skill) that the leader card can own (it can be an empty list)
     * @param whiteMarbleSubstitutions list of substitutions with white marbles (special skill) that the leader card can own
* (it can be an empty list)
     * @param victoryPoints number of victory points that the card gives
     * @param canBeActivated states if the card can be activated
     */
    public ServerLeaderCardRepresentation(
        String itemID,
        GameItemsManager gameItemsManager,
        LeaderCardState state,
        List<ServerLeaderCardRequirementRepresentation> requirements,
        List<ServerProductionRepresentation> productions,
        List<ServerResourceStorageRepresentation> resourceStorages,
        List<ServerDevelopmentCardCostDiscountRepresentation> cardCostDiscounts,
        List<ServerWhiteMarbleSubstitutionRepresentation> whiteMarbleSubstitutions,
        int victoryPoints,
        boolean canBeActivated) {
        super(itemID, gameItemsManager);
        this.state = state;
        this.requirements = requirements;
        this.productions = productions;
        this.resourceStorages = resourceStorages;
        this.cardCostDiscounts = cardCostDiscounts;
        this.whiteMarbleSubstitutions = whiteMarbleSubstitutions;
        this.victoryPoints = victoryPoints;
        this.canBeActivated = canBeActivated;
    }
}
