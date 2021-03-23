package it.polimi.ingsw.model.storage;

import it.polimi.ingsw.model.gamecontext.playercontext.Map_ResourceType,Integer_;

public class SameResourceTypeRule extends ResourceStorageRule {

	public SameResourceTypeRule() {

	}

	public boolean checkRule(ResourceStorage storage, Map_ResourceType,Integer_ newResources) {
		return false;
	}

}
