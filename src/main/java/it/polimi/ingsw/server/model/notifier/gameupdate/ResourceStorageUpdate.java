package it.polimi.ingsw.server.model.notifier.gameupdate;


import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class ResourceStorageUpdate extends GameUpdate {

	public Map<ResourceType, Integer> resourcesInStorage;

}
