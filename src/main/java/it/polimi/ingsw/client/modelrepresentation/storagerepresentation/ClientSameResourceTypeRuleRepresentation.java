package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import it.polimi.ingsw.localization.Localization;

import java.util.Optional;

public class ClientSameResourceTypeRuleRepresentation extends ClientResourceStorageRuleRepresentation {

    @Override
    public Optional<String> getDescription() {
        return Optional.of(
            Localization.getLocalizationInstance().getString("resourceStorages.rules.sameResource")
        );
    }

}
