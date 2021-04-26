package it.polimi.ingsw.network.clientrequest;

import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.network.clientrequest.validator.ManageResourcesFromMarketClientRequestValidator;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.Map;

public class ManageResourcesFromMarketClientRequest extends ClientRequest {

    public final Map<ResourceStorage, Map<ResourceType, Integer>> resourcesToAddByStorage;

    public final Map<ResourceStorage, Map<ResourceType, Integer>> starResourcesChosenToAddByStorage;

    public ManageResourcesFromMarketClientRequest(
        Player player, Map<ResourceStorage,
        Map<ResourceType, Integer>> resourcesToAddByStorage,
        Map<ResourceStorage, Map<ResourceType, Integer>> starResourcesChosenToAddByStorage
    ) {
        super(player);
        this.resourcesToAddByStorage = resourcesToAddByStorage;
        this.starResourcesChosenToAddByStorage = starResourcesChosenToAddByStorage;
    }

    public Map<Player, ServerMessage> callHandler(GameState state) throws ResourceStorageRuleViolationException {
		return(state.handleRequestManageResourcesFromMarket(this));
	}

    @Override
    public ClientRequestValidator getValidator() {
        return new ManageResourcesFromMarketClientRequestValidator();
    }

}
