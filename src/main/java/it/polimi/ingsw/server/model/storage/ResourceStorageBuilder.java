package it.polimi.ingsw.server.model.storage;

import java.util.ArrayList;
import java.util.List;

public class ResourceStorageBuilder {
	private List<ResourceStorageRule> rules = new ArrayList<>();;

	public static ResourceStorageBuilder initResourceStorageBuilder() {
		return new ResourceStorageBuilder();
	}

	public ResourceStorage createResourceStorage() {
		return new ResourceStorage(rules);
	}

	public ResourceStorageBuilder addRule(ResourceStorageRule rule) {
		rules.add(rule);
		return this;
	}

}
