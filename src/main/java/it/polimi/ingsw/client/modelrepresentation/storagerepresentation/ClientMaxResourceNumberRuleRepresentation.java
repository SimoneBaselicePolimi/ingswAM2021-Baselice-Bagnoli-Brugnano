package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;

import java.util.Map;
import java.util.Optional;

public class ClientMaxResourceNumberRuleRepresentation extends ClientResourceStorageRuleRepresentation {

	/**
	 * Max number of resources that the storage can contain
	 */
	private final int maxResources;

	public ClientMaxResourceNumberRuleRepresentation(
		@JsonProperty("maxResources") int maxResources
	) {
		this.maxResources = maxResources;
	}

	public int getMaxResources() {
		return maxResources;
	}

	public Optional<String> getErrorMessageIfPresent(
		ClientResourceStorageRepresentation storage,
		Map<ResourceType,Integer> newResources
	) {
		int totalNumOfResources = ResourceUtils.sum(storage.getResources(), newResources).values()
			.stream().mapToInt(Integer::intValue).sum();
		if (totalNumOfResources > maxResources)
			return Optional.of(Localization.getLocalizationInstance().getString(
				"client.cli.resourcesRepositioning.checkRuleErrorMessage.maxNumberRule",
				maxResources
			));
		else
			return Optional.empty();
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of(Localization.getLocalizationInstance().getString(
			"resourceStorages.rules.maxResourceNumber",
			maxResources,
			maxResources == 1 ? Localization.getLocalizationInstance().getString("resources.singular")
				: Localization.getLocalizationInstance().getString("resources.plural")
			)
		);
	}
}
