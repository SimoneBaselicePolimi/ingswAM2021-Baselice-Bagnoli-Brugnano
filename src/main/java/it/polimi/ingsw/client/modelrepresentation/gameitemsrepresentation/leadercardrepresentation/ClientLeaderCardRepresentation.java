package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientDevelopmentCardCostDiscountRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientRegisteredIdentifiableItemRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientWhiteMarbleSubstitutionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;

import java.util.List;

public class ClientLeaderCardRepresentation extends ClientRegisteredIdentifiableItemRepresentation {

    private LeaderCardState state;
    private final List<ClientLeaderCardRequirementRepresentation> requirements;
    private final List<ClientProductionRepresentation> productions;
    private final List<ClientResourceStorageRepresentation> resourceStorages;
    private final List<ClientDevelopmentCardCostDiscountRepresentation> cardCostDiscounts;
    private final List<ClientWhiteMarbleSubstitutionRepresentation> whiteMarbleSubstitutions;
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
    public ClientLeaderCardRepresentation(
        @JsonProperty("itemID") String itemID,
        @JacksonInject("gameItemsManager") GameItemsManager gameItemsManager,
        @JsonProperty("state") LeaderCardState state,
        @JsonProperty("requirements") List<ClientLeaderCardRequirementRepresentation> requirements,
        @JsonProperty("productions") List<ClientProductionRepresentation> productions,
        @JsonProperty("resourceStorages") List<ClientResourceStorageRepresentation> resourceStorages,
        @JsonProperty("cardCostDiscounts") List<ClientDevelopmentCardCostDiscountRepresentation> cardCostDiscounts,
        @JsonProperty("whiteMarbleSubstitutions") List<ClientWhiteMarbleSubstitutionRepresentation> whiteMarbleSubstitutions,
        @JsonProperty("victoryPoints") int victoryPoints,
        @JsonProperty("canBeActivated") boolean canBeActivated
    ) {
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

    public List<ClientLeaderCardRequirementRepresentation> getRequirements() {
        return requirements;
    }

    public List<ClientProductionRepresentation> getProductions() {
        return productions;
    }

    public List<ClientResourceStorageRepresentation> getResourceStorages() {
        return resourceStorages;
    }

    public List<ClientDevelopmentCardCostDiscountRepresentation> getCardCostDiscounts() {
        return cardCostDiscounts;
    }

    public List<ClientWhiteMarbleSubstitutionRepresentation> getWhiteMarbleSubstitutions() {
        return whiteMarbleSubstitutions;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public boolean canBeActivated() {
        return canBeActivated;
    }

    public void setCanBeActivated(boolean canBeActivated) {
        this.canBeActivated = canBeActivated;
    }
}
