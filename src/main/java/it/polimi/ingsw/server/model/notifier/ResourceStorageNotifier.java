package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRule;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResourceStorageNotifier extends ResourceStorage implements Notifier {

	protected ResourceStorageNotifier(String resourceStorageID, GameItemsManager gameItemsManager, List<ResourceStorageRule> rules) {
		super(resourceStorageID, gameItemsManager, rules);
	}

	@Override
	public Set<ServerGameUpdate> getUpdates() {
		return null;
	}

	public void addResources(Map<ResourceType,Integer> newResources) {

	}

	@Override
	public Map<ResourceType,Integer> removeResources(Map<ResourceType, Integer> resources) {
		return null;
	}


}
