package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;

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
