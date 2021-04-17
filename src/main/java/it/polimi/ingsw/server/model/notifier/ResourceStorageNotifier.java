package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRule;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResourceStorageNotifier extends ResourceStorage implements Notifier {

	public ResourceStorageNotifier(List<ResourceStorageRule> rules, String resourceID) {
		super(rules, resourceID);
	}

	@Override
	public Set<GameUpdate> getUpdates() {
		return null;
	}

	public void addResources(Map<ResourceType,Integer> newResources) {

	}

	@Override
	public Map<ResourceType,Integer> removeResources(Map<ResourceType, Integer> resources) {
		return null;
	}


}
