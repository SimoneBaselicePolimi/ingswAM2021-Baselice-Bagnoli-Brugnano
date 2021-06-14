package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClientDifferentResourceTypesInDifferentStoragesRuleRepresentation extends ClientResourceStorageRuleRepresentation {

	/**
	 * List of storages that implement this specific rule
	 */
	private final List<ClientResourceStorageRepresentation> storages;

	public ClientDifferentResourceTypesInDifferentStoragesRuleRepresentation(
		@JsonProperty("storages") List<ClientResourceStorageRepresentation> storages
	) {
		this.storages = storages;
	}

	public List<ClientResourceStorageRepresentation> getStorages() {
		return storages;
	}

	public Optional<String> getErrorMessageIfPresent(
		ClientResourceStorageRepresentation storage,
		Map<ResourceType,Integer> newResources
	) {
		for (ClientResourceStorageRepresentation s :  storages){
			for (ResourceType resourceInStorage: s.getResources().keySet()) {
				if (!s.equals(storage)){
					for (ResourceType resourceType : newResources.keySet()) {
						if (resourceType.equals(resourceInStorage))
							return Optional.of(Localization.getLocalizationInstance().getString(
								"client.cli.resourcesRepositioning.checkRuleErrorMessage.differentResourceTypeInDifferentStorages"
							));
					}
				}
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.empty();
	}

}
