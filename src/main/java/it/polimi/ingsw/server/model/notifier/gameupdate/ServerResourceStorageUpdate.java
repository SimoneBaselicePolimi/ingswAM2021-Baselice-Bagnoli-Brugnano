package it.polimi.ingsw.server.model.notifier.gameupdate;


import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Map;

public class ServerResourceStorageUpdate extends ServerGameUpdate {

	@SerializeIdOnly
	public ResourceStorage storage;

	public Map<ResourceType, Integer> resourcesInStorage;

	public ServerResourceStorageUpdate(
		@JsonProperty("storage") ResourceStorage storage,
		@JsonProperty("resourcesInStorage") Map<ResourceType, Integer> resourcesInStorage
	) {
		this.storage = storage;
		this.resourcesInStorage = resourcesInStorage;
	}

}
