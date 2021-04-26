package it.polimi.ingsw.network.clientrequest;

import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.network.clientrequest.validator.ProductionActionClientRequestValidator;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.Map;
import java.util.Set;

public class ProductionActionClientRequest extends ClientRequest {
    public final Set<Production> productions;
    public final Map<ResourceType, Integer> starResourceCost;
    public final Map<ResourceType, Integer> starResourceReward;

    public ProductionActionClientRequest(
        Player player,
        Set<Production> productions,
        Map<ResourceType, Integer> starResourceCost,
        Map<ResourceType, Integer> starResourceReward
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
