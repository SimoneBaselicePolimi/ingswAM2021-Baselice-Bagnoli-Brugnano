package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class ServerProductionRepresentation extends ServerRegisteredIdentifiableItemRepresentation {

    /**
     * Production cost made of specific type and number of Resources
     */
    public final Map<ResourceType, Integer> resourceCost;

    /**
     * Production reward made of specific type and number of Resources
     */
    public final Map<ResourceType, Integer> resourceReward;

    /**
     * Production cost made of a generic type of Resource (Player can choose), in a fixed quantity
     */
    public final int starResourceCost;

    /**
     * Production reward made of a generic type of Resource (Player can choose), in a fixed quantity
     */
    public final int starResourceReward;

    /**
     * Production reward made of a fixed number of Faith Points
     */
    public final int faithReward;

    /**
     * ProductionRepresentation constructor.
     * @param itemID ID which identifies this specific Production Item
     * @param resourceCost cost made of specific type and number of Resources
     * @param resourceReward reward made of specific type and number of Resources
     * @param starResourceCost cost made of a generic type of Resource, in a fixed quantity
     * @param starResourceReward reward made of a generic type of Resource, in a fixed quantity
     * @param faithReward reward made of a fixed number of Faith Points
     */
    public ServerProductionRepresentation(
        @JsonProperty("itemID") String itemID,
        @JsonProperty("resourceCost") Map<ResourceType, Integer> resourceCost,
        @JsonProperty("resourceReward") Map<ResourceType, Integer> resourceReward,
        @JsonProperty("starResourceCost") int starResourceCost,
        @JsonProperty("starResourceReward") int starResourceReward,
        @JsonProperty("faithReward") int faithReward
    ) {
        super(itemID);
        this.resourceCost = resourceCost;
        this.resourceReward = resourceReward;
        this.starResourceCost = starResourceCost;
        this.starResourceReward = starResourceReward;
        this.faithReward = faithReward;
    }
}
