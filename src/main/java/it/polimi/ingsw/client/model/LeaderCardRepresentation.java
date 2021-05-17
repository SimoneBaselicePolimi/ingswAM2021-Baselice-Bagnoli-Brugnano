package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;

import java.util.List;

public class LeaderCardRepresentation extends RegisteredIdentifiableItemRepresentation{

    private LeaderCardState state;
    private final List<LeaderCardRequirementRepresentation> requirements;
    private final List<ProductionRepresentation> productions;
    private final List<ResourceStorageRepresentation> resourceStorages;
    private final List<DevelopmentCardCostDiscountRepresentation> cardCostDiscounts;
    private final List<WhiteMarbleSubstitutionRepresentation> whiteMarbleSubstitutions;
    private final int victoryPoints;
    private boolean canBeActivated;

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
    public LeaderCardRepresentation(
        String itemID,
        GameItemsManager gameItemsManager,
        LeaderCardState state,
        List<LeaderCardRequirementRepresentation> requirements,
        List<ProductionRepresentation> productions,
        List<ResourceStorageRepresentation> resourceStorages,
        List<DevelopmentCardCostDiscountRepresentation> cardCostDiscounts,
        List<WhiteMarbleSubstitutionRepresentation> whiteMarbleSubstitutions,
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

    public LeaderCardState getState() {
        return state;
    }

    public void setState(LeaderCardState state) {
        this.state = state;
    }

    public List<LeaderCardRequirementRepresentation> getRequirements() {
        return requirements;
    }

    public List<ProductionRepresentation> getProductions() {
        return productions;
    }

    public List<ResourceStorageRepresentation> getResourceStorages() {
        return resourceStorages;
    }

    public List<DevelopmentCardCostDiscountRepresentation> getCardCostDiscounts() {
        return cardCostDiscounts;
    }

    public List<WhiteMarbleSubstitutionRepresentation> getWhiteMarbleSubstitutions() {
        return whiteMarbleSubstitutions;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public boolean isCanBeActivated() {
        return canBeActivated;
    }

    public void setCanBeActivated(boolean canBeActivated) {
        this.canBeActivated = canBeActivated;
    }
}
