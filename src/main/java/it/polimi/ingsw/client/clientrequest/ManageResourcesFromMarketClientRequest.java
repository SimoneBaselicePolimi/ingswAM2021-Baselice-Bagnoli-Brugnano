package it.polimi.ingsw.client.clientrequest;

import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.network.clientrequest.validator.ManageResourcesFromMarketClientRequestValidator;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.Map;

public class ManageResourcesFromMarketClientRequest extends ClientRequest {

    public final Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> resourcesToAddByStorage;

    public final Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> starResourcesChosenToAddByStorage;

    public final Map<ResourceType, Integer> resourcesLeftInTemporaryStorage;

    public ManageResourcesFromMarketClientRequest(
        Player player, Map<ClientResourceStorageRepresentation,
        Map<ResourceType, Integer>> resourcesToAddByStorage,
        Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> starResourcesChosenToAddByStorage,
        Map<ResourceType, Integer> resourcesLeftInTemporaryStorage) {
        super(player);
        this.resourcesToAddByStorage = resourcesToAddByStorage;
        this.starResourcesChosenToAddByStorage = starResourcesChosenToAddByStorage;
        this.resourcesLeftInTemporaryStorage = resourcesLeftInTemporaryStorage;
    }

}
