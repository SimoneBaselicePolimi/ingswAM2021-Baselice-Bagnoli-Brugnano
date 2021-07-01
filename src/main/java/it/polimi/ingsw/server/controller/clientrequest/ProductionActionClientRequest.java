package it.polimi.ingsw.server.controller.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.controller.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.server.controller.clientrequest.validator.ProductionActionClientRequestValidator;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;

import java.util.Map;
import java.util.Set;

public class ProductionActionClientRequest extends ClientRequest {

    @SerializeAsSetOfIds
    public final Set<Production> productions;

    public final Map<ResourceType, Integer> starResourceCost;

    public final Map<ResourceType, Integer> starResourceReward;

    public ProductionActionClientRequest(
        @JsonProperty("player") Player player,
        @JsonProperty("productions") Set<Production> productions,
        @JsonProperty("starResourceCost") Map<ResourceType, Integer> starResourceCost,
        @JsonProperty("starResourceReward") Map<ResourceType, Integer> starResourceReward
    ) {
        super(player);
        this.productions = productions;
        this.starResourceCost = starResourceCost;
        this.starResourceReward = starResourceReward;
    }

    public Map<Player, ServerMessage> callHandler(GameState state) throws NotEnoughResourcesException, ResourceStorageRuleViolationException {
		return(state.handleRequestProductionAction(this));
	}

    @Override
    public ClientRequestValidator getValidator() {
        return new ProductionActionClientRequestValidator();
    }

}
