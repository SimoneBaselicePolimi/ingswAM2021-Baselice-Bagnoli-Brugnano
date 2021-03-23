package it.polimi.ingsw.model.notifier;

import it.polimi.ingsw.model.storage.ResourceStorage;
import it.polimi.ingsw.model.gamecontext.playercontext.Map_ResourceType,Integer_;

public class ResourceStorageNotifier extends ResourceStorage implements Notifier {

	public Option<ResourceStorageUpdate> getUpdate() {
		return null;
	}

	public void addResources(Map_ResourceType,Integer_ resources) {

	}

	public Map_ResourceType,Integer_ removeResources(Map<ResourceType,int> resources) {
		return null;
	}


	/**
	 * @see Notifier#getUpdate()
	 */
	public Optional<GameUpdate> getUpdate() {
		return null;
	}

}
