package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gui.fxcontrollers.components.NumOfResourcesRequirement;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

public class ClientResourceNumberRequirementRepresentation extends ClientLeaderCardRequirementRepresentation {

    ResourceType resourceType;
    int resourceNumber;

    /**
     * ResourceNumberRequirementRepresentation constructor
     * @param resourceType type of resource required
     * @param resourceNumber number of resource required
     */
    public ClientResourceNumberRequirementRepresentation(
        @JsonProperty("resourceType") ResourceType resourceType,
        @JsonProperty("resourceNumber") int resourceNumber
    ) {
        this.resourceType = resourceType;
        this.resourceNumber = resourceNumber;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getResourceNumber() {
        return resourceNumber;
    }

    @Override
    public String getDescription() {
        return Localization.getLocalizationInstance().getString(
            "leaderCards.requirements.resourceNumber",
            resourceNumber,
            resourceNumber == 1 ? resourceType.getLocalizedNameSingular() : resourceType.getLocalizedNamePlural()
        );
    }

    @Override
    public NumOfResourcesRequirement buildGuiComponent() {
        return new NumOfResourcesRequirement(this);
    }

}
