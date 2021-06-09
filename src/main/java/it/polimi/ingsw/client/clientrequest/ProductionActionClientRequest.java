package it.polimi.ingsw.client.clientrequest;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;
import java.util.Set;

public class ProductionActionClientRequest extends ClientRequest {
    public final Set<ClientProductionRepresentation> productions;
    public final Map<ResourceType, Integer> starResourceCost;
    public final Map<ResourceType, Integer> starResourceReward;

    public ProductionActionClientRequest(
        Player player,
        Set<ClientProductionRepresentation> productions,
        Map<ResourceType, Integer> starResourceCost,
        Map<ResourceType, Integer> starResourceReward
    ) {
        super(player);
        this.productions = productions;
        this.starResourceCost = starResourceCost;
        this.starResourceReward = starResourceReward;
    }

}
