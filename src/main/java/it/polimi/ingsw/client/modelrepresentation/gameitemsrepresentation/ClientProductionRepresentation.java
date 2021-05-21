package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class ClientProductionRepresentation extends ClientRegisteredIdentifiableItemRepresentation {

    /**
     * Production cost made of specific type and number of Resources
     */
    private final Map<ResourceType, Integer> resourceCost;

    /**
     * Production reward made of specific type and number of Resources
     */
    private final Map<ResourceType, Integer> resourceReward;

    /**
     * Production cost made of a generic type of Resource (Player can choose), in a fixed quantity
     */
    private final int starResourceCost;

    /**
     * Production reward made of a generic type of Resource (Player can choose), in a fixed quantity
     */
    private final int starResourceReward;

    /**
     * Production reward made of a fixed number of Faith Points
     */
    private final int faithReward;

    /**
     * ProductionRepresentation constructor.
     * @param itemID ID which identifies this specific Production Item
     * @param gameItemsManager a reference to gameItemsManager is needed to register the new Production object
     *                          (see {@link RegisteredIdentifiableItem})
     * @param resourceCost cost made of specific type and number of Resources
     * @param resourceReward reward made of specific type and number of Resources
     * @param starResourceCost cost made of a generic type of Resource, in a fixed quantity
     * @param starResourceReward reward made of a generic type of Resource, in a fixed quantity
     * @param faithReward reward made of a fixed number of Faith Points
     */
    public ClientProductionRepresentation(
        @JsonProperty("itemID") String itemID,
        @JacksonInject("gameItemsManager") GameItemsManager gameItemsManager,
        @JsonProperty("resourceCost") Map<ResourceType, Integer> resourceCost,
        @JsonProperty("resourceReward") Map<ResourceType, Integer> resourceReward,
        @JsonProperty("starResourceCost") int starResourceCost,
        @JsonProperty("starResourceReward") int starResourceReward,
        @JsonProperty("faithReward") int faithReward
    ) {
        super(itemID, gameItemsManager);
        this.resourceCost = resourceCost;
        this.resourceReward = resourceReward;
        this.starResourceCost = starResourceCost;
        this.starResourceReward = starResourceReward;
        this.faithReward = faithReward;
    }

    public Map<ResourceType, Integer> getResourceCost() {
        return resourceCost;
    }

    public Map<ResourceType, Integer> getResourceReward() {
        return resourceReward;
    }

    public int getStarResourceCost() {
        return starResourceCost;
    }

    public int getStarResourceReward() {
        return starResourceReward;
    }

    public int getFaithReward() {
        return faithReward;
    }
}
