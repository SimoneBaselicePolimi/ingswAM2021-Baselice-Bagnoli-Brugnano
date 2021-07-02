package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientDifferentResourceTypesInDifferentStoragesRuleRepresentation extends ClientResourceStorageRuleRepresentation {


	private final List<String> storagesID;

	/**
	 * List of storages that implement this specific rule
	 */
	private List<ClientResourceStorageRepresentation> storages;

	public ClientDifferentResourceTypesInDifferentStoragesRuleRepresentation(
		@JsonProperty("storagesID") List<String> storagesID
	) {
		this.storagesID = storagesID;
	}

	public List<ClientResourceStorageRepresentation> getStorages() {
		if(storages == null)
			storages = storagesID.stream()
				.map(id -> ClientManager.getInstance().getGameItemsManager().getItem(
					ClientResourceStorageRepresentation.class,
					id
				)).collect(Collectors.toList());
		return storages;
	}

	public Optional<String> getErrorMessageIfPresent(
		ClientResourceStorageRepresentation storage,
		Map<ResourceType,Integer> newResources
	) {
		if(storages == null)
			getStorages();

		for (
			ResourceType newResType : newResources.entrySet().stream()
			.filter(e -> e.getValue() > 0)
			.map(Map.Entry::getKey)
			.collect(Collectors.toList())
		) {
			if(
				storages.stream()
				.filter(s -> !s.equals(storage))
				.map(ClientResourceStorageRepresentation::getResources)
				.filter(res -> res.containsKey(newResType))
				.anyMatch(res -> res.get(newResType) > 0)
			) {
				return Optional.of(Localization.getLocalizationInstance().getString(
					"client.cli.resourcesRepositioning.checkRuleErrorMessage.differentResourceTypeInDifferentStorages"
				));
			}
		}

		return Optional.empty();
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.empty();
	}

}
