package it.polimi.ingsw.model.storage;

import it.polimi.ingsw.model.gamecontext.playercontext.Map_ResourceType,Integer_;

public class DifferentResourceTypesInDifferentStoragesRule extends ResourceStorageRule {

	public DifferentResourceTypesInDifferentStoragesRule() {

	}

	public boolean checkRule(ResourceStorage storage, Map_ResourceType,Integer_ newResources) {
		return false;
	}

}
