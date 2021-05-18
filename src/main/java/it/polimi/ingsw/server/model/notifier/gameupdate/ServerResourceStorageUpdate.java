package it.polimi.ingsw.server.model.notifier.gameupdate;


import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Map;

public class ServerResourceStorageUpdate extends ServerGameUpdate {

	public ServerResourceStorageUpdate(ResourceStorage storage, Map<ResourceType, Integer> resourcesInStorage) {
		this.storage = storage;
		this.resourcesInStorage = resourcesInStorage;
	}

	@SerializeIdOnly
	public ResourceStorage storage;

	public Map<ResourceType, Integer> resourcesInStorage;

}
