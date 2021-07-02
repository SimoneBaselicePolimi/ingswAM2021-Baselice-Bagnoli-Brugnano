package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;
import java.util.Optional;

public class ClientSameResourceTypeRuleRepresentation extends ClientResourceStorageRuleRepresentation {

    public Optional<String> getErrorMessageIfPresent(
        ClientResourceStorageRepresentation storage,
        Map<ResourceType,Integer> newResources
    ) {
        if (newResources.size() > 1)
            return Optional.of(Localization.getLocalizationInstance().getString(
                "client.cli.resourcesRepositioning.checkRuleErrorMessage.sameResourceType"
            ));
        if(newResources.isEmpty() ||
            storage.getResources().isEmpty() ||
            storage.getResources().keySet().iterator().next() == newResources.keySet().iterator().next())
            return Optional.empty();
        else
            return Optional.of(Localization.getLocalizationInstance().getString(
                "client.cli.resourcesRepositioning.checkRuleErrorMessage.sameResourceType"
            ));
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.of(
            Localization.getLocalizationInstance().getString("resourceStorages.rules.sameResource")
        );
    }

}
