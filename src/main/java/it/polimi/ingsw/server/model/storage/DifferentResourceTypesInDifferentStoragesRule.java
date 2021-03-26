package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DifferentResourceTypesInDifferentStoragesRule extends ResourceStorageRule {
	/**
	 * list of storage that implement this specific rule
	 */
	private List<ResourceStorage> storages = new ArrayList<>();

	/**
	 * DifferentResourceTypesInDifferentStoragesRule Constructor
	 */
	public DifferentResourceTypesInDifferentStoragesRule() {
	}

	@Override
	public boolean checkRule(ResourceStorage storage, Map<ResourceType,Integer> newResources) {
			if (!storages.contains(storage))
				storages.add(storage);
			for (int i = 0; i < storages.size(); i++){
				for (ResourceType resourcePresentInStorage: storages.get(i).peekResources().keySet()) {
					if (storages.get(i) != storage){
						for (ResourceType resourceType : newResources.keySet()) {
							if (resourceType == resourcePresentInStorage)
									return false;
						}
					}
				}
			}
			return true;
	}

}
