package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.notifier.gameupdate.ResourceStorageUpdate;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.Map;
import java.util.Optional;

public class ResourceStorageNotifier extends ResourceStorage implements Notifier<ResourceStorageUpdate> {

	@Override
	public Optional<ResourceStorageUpdate> getUpdate() {
		return null;
	}

	public void addResources(Map<ResourceType,Integer> resources) {

	}

	@Override
	public Map<ResourceType,Integer> removeResources(Map<ResourceType, Integer> resources) {
		return null;
	}


}
