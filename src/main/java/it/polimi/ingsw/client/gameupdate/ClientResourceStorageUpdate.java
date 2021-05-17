package it.polimi.ingsw.client.gameupdate;


import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.ResourceStorageUpdateHandler;
import it.polimi.ingsw.client.model.ResourceStorageRepresentation;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Map;

public class ClientResourceStorageUpdate extends ClientGameUpdate {

	@SerializeIdOnly
	public ResourceStorageRepresentation storage;

	public Map<ResourceType, Integer> resourcesInStorage;

	@Override
	public GameUpdateHandler getHandler() {
		return new ResourceStorageUpdateHandler();
	}
}
