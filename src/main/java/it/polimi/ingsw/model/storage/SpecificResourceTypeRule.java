package it.polimi.ingsw.model.storage;

import it.polimi.ingsw.model.gamecontext.playercontext.Map_ResourceType,Integer_;

public class SpecificResourceTypeRule extends ResourceStorageRule {

	public SpecificResourceType(int resourceType) {

	}

	public boolean checkRule(ResourceStorage storage, Map_ResourceType,Integer_ newResources) {
		return false;
	}

}
