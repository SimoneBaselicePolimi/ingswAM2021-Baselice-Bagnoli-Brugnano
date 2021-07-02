package it.polimi.ingsw.client.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;

import java.util.Map;
import java.util.Set;

public class ProductionActionClientRequest extends ClientRequest {

    @SerializeAsSetOfIds
    public final Set<ClientProductionRepresentation> productions;

    public final Map<ResourceType, Integer> starResourceCost;

    public final Map<ResourceType, Integer> starResourceReward;

    public ProductionActionClientRequest(
        @JsonProperty("player") Player player,
        @JsonProperty("productions") Set<ClientProductionRepresentation> productions,
        @JsonProperty("starResourceCost") Map<ResourceType, Integer> starResourceCost,
        @JsonProperty("starResourceReward") Map<ResourceType, Integer> starResourceReward
    ) {
        super(player);
        this.productions = productions;
        this.starResourceCost = starResourceCost;
        this.starResourceReward = starResourceReward;
    }

}
