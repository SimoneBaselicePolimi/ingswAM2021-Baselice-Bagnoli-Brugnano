package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerDevelopmentCardCostDiscountRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerProductionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerRegisteredIdentifiableItemRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerWhiteMarbleSubstitutionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.storagerepresentation.ServerResourceStorageRepresentation;

import java.util.Set;

public class ServerLeaderCardRepresentation extends ServerRegisteredIdentifiableItemRepresentation {

    public final LeaderCardState state;
    public final Set<ServerLeaderCardRequirementRepresentation> requirements;
    public final Set<ServerProductionRepresentation> productions;
    public final Set<ServerResourceStorageRepresentation> resourceStorages;
    public final Set<ServerDevelopmentCardCostDiscountRepresentation> cardCostDiscounts;
    public final Set<ServerWhiteMarbleSubstitutionRepresentation> whiteMarbleSubstitutions;
    public final int victoryPoints;
    public final boolean canBeActivated;

    /**
     * LeaderCardRepresentation constructor
     * @param itemID ID of the leader card, see {@link IdentifiableItem}
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
        @JsonProperty("itemID") String itemID,
        @JsonProperty("state") LeaderCardState state,
        @JsonProperty("requirements") Set<ServerLeaderCardRequirementRepresentation> requirements,
        @JsonProperty("productions") Set<ServerProductionRepresentation> productions,
        @JsonProperty("resourceStorages") Set<ServerResourceStorageRepresentation> resourceStorages,
        @JsonProperty("cardCostDiscounts") Set<ServerDevelopmentCardCostDiscountRepresentation> cardCostDiscounts,
        @JsonProperty("whiteMarbleSubstitutions") Set<ServerWhiteMarbleSubstitutionRepresentation> whiteMarbleSubstitutions,
        @JsonProperty("victoryPoints") int victoryPoints,
        @JsonProperty("canBeActivated") boolean canBeActivated
    ) {
        super(itemID);
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
