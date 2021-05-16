package it.polimi.ingsw.client.gameupdate;


import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class ClientResourceStorageUpdate extends ClientGameUpdate {

	public Map<ResourceType, Integer> resourcesInStorage;

}
