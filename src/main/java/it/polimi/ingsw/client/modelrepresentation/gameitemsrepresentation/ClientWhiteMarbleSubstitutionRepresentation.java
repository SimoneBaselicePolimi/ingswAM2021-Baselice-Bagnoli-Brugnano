package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

public class ClientWhiteMarbleSubstitutionRepresentation extends ClientRegisteredIdentifiableItemRepresentation {

    /**
     * Resource type the Player can substitute to a White Marble taken from the Market
     */
    private final ResourceType resourceTypeToSubstitute;

    /**
     * WhiteMarbleSubstitutionRepresentation constructor.
     * @param resourceTypeToSubstitute resource type to substitute to a White Marble
     */
    public ClientWhiteMarbleSubstitutionRepresentation(
        @JsonProperty("itemID") String itemID,
        @JacksonInject("gameItemsManager") GameItemsManager gameItemsManager,
        @JsonProperty("resourceTypeToSubstitute") ResourceType resourceTypeToSubstitute
    ) {
        super(itemID, gameItemsManager);
        this.resourceTypeToSubstitute = resourceTypeToSubstitute;
    }

    public String getDescription() {
        return Localization.getLocalizationInstance().getString(
            "leaderCards.specialPowers.marbleSubstitution",
            resourceTypeToSubstitute.getLocalizedNameSingular()
        ) + "\n";
    }

}
