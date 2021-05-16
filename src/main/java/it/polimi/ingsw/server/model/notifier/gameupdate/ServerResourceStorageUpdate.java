package it.polimi.ingsw.server.model.notifier.gameupdate;


import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class ServerResourceStorageUpdate extends ServerGameUpdate {

	public Map<ResourceType, Integer> resourcesInStorage;

}
