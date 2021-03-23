package it.polimi.ingsw.model.notifier;

import it.polimi.ingsw.model.gameitems.ResourceType;
import it.polimi.ingsw.model.notifier.gameupdate.GameUpdate;
import it.polimi.ingsw.model.notifier.gameupdate.ResourceStorageUpdate;
import it.polimi.ingsw.model.storage.ResourceStorage;
import jdk.nashorn.internal.runtime.options.Option;

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
