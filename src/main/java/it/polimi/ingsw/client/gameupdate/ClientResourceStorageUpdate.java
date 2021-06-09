package it.polimi.ingsw.client.gameupdate;


import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.ResourceStorageUpdateHandler;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Map;

public class ClientResourceStorageUpdate extends ClientGameUpdate {

	@SerializeIdOnly
	public ClientResourceStorageRepresentation storage;

	public Map<ResourceType, Integer> resourcesInStorage;

	public ClientResourceStorageUpdate(
		@JsonProperty("storage") ClientResourceStorageRepresentation storage,
		@JsonProperty("resourcesInStorage") Map<ResourceType, Integer> resourcesInStorage
	) {
		this.storage = storage;
		this.resourcesInStorage = resourcesInStorage;
	}

	@Override
	public GameUpdateHandler getHandler() {
		return new ResourceStorageUpdateHandler();
	}
}
