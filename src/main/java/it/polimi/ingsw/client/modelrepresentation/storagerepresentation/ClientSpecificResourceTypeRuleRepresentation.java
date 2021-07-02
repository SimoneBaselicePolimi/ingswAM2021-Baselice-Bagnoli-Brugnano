package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;
import java.util.Optional;

public class ClientSpecificResourceTypeRuleRepresentation extends ClientResourceStorageRuleRepresentation {

	/**
	 * Specific type of resource that the storage can contain
	 */
	private final ResourceType resourceType;

	public ClientSpecificResourceTypeRuleRepresentation(
		@JsonProperty("resourceType") ResourceType resourceType
	) {
		this.resourceType = resourceType;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public Optional<String> getErrorMessageIfPresent(
		ClientResourceStorageRepresentation storage,
		Map<ResourceType,Integer> newResources
	) {
		if (newResources.isEmpty())
			return Optional.empty();
		for (ResourceType resource : newResources.keySet()){
			if (resource != resourceType)
				return Optional.of(Localization.getLocalizationInstance().getString(
					"client.cli.resourcesRepositioning.checkRuleErrorMessage.specificResourceType",
					resourceType.getLocalizedNamePlural()
				));
		}
		return Optional.empty();
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of(Localization.getLocalizationInstance().getString(
			"resourceStorages.rules.specificResourceNumber",
			resourceType.getLocalizedNamePlural()
			)
		);
	}

}
