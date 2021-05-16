package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.*;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirement;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.List;

public class LeaderCardRepresentation extends RegisteredIdentifiableItemRepresentation{

    private LeaderCardState state;
    private List<LeaderCardRequirement> requirements;
    private List<Production> productions;
    private List<ResourceStorage> resourceStorages;
    private List<DevelopmentCardCostDiscount> cardCostDiscounts;
    private List<WhiteMarbleSubstitution> whiteMarbleSubstitutions;
    int victoryPoints;

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
     */
    public LeaderCardRepresentation(
        String itemID,
        GameItemsManager gameItemsManager,
        LeaderCardState state,
        List<LeaderCardRequirement> requirements,
        List<Production> productions,
        List<ResourceStorage> resourceStorages,
        List<DevelopmentCardCostDiscount> cardCostDiscounts,
        List<WhiteMarbleSubstitution> whiteMarbleSubstitutions,
        int victoryPoints
    ) {
        super(itemID, gameItemsManager);
        this.state = state;
        this.requirements = requirements;
        this.productions = productions;
        this.resourceStorages = resourceStorages;
        this.cardCostDiscounts = cardCostDiscounts;
        this.whiteMarbleSubstitutions = whiteMarbleSubstitutions;
        this.victoryPoints = victoryPoints;
    }

    public LeaderCardState getState() {
        return state;
    }

    public void setState(LeaderCardState state) {
        this.state = state;
    }

    public List<LeaderCardRequirement> getRequirements() {
        return requirements;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public List<ResourceStorage> getResourceStorages() {
        return resourceStorages;
    }

    public List<DevelopmentCardCostDiscount> getCardCostDiscounts() {
        return cardCostDiscounts;
    }

    public List<WhiteMarbleSubstitution> getWhiteMarbleSubstitutions() {
        return whiteMarbleSubstitutions;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}
