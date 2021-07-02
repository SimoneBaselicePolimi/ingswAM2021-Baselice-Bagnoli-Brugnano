package it.polimi.ingsw.server.controller.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.controller.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.server.controller.clientrequest.validator.ManageResourcesFromMarketClientRequestValidator;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;

import java.util.Map;

public class ManageResourcesFromMarketClientRequest extends ClientRequest {

    @SerializeAsMapWithIdKey
    public final Map<ResourceStorage, Map<ResourceType, Integer>> resourcesInModifiedStorages;

    public final Map<ResourceType, Integer> resourcesLeftInTemporaryStorage;

    public ManageResourcesFromMarketClientRequest(
        @JsonProperty("player") Player player,
        @JsonProperty("resourcesInModifiedStorages") Map<ResourceStorage, Map<ResourceType, Integer>> resourcesInModifiedStorages,
        @JsonProperty("resourcesLeftInTemporaryStorage") Map<ResourceType, Integer> resourcesLeftInTemporaryStorage
    ) {
        super(player);
        this.resourcesInModifiedStorages = resourcesInModifiedStorages;
        this.resourcesLeftInTemporaryStorage = resourcesLeftInTemporaryStorage;
    }

    public Map<Player, ServerMessage> callHandler(GameState state) throws ResourceStorageRuleViolationException, NotEnoughResourcesException {
		return(state.handleRequestManageResourcesFromMarket(this));
	}

    @Override
    public ClientRequestValidator getValidator() {
        return new ManageResourcesFromMarketClientRequestValidator();
    }

}
