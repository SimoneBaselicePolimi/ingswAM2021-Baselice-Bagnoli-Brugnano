package it.polimi.ingsw.model.storage;

import it.polimi.ingsw.model.gamecontext.playercontext.Map_ResourceType,Integer_;

public class MaxResourceNumberRule extends ResourceStorageRule {

	public MaxResourceNumberRule(int maxResources) {

	}

	public boolean checkRule(ResourceStorage storage, Map_ResourceType,Integer_ newResources) {
		return false;
	}

}
