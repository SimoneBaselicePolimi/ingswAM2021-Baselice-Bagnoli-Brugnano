package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "requirementType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ClientDevelopmentCardColourAndLevelRequirementRepresentation.class, name = "DevelopmentCardColourAndLevelRequirementRepresentation"),
    @JsonSubTypes.Type(value = ClientDevelopmentCardColourRequirementRepresentation.class, name = "DevelopmentCardColourRequirementRepresentation"),
    @JsonSubTypes.Type(value = ClientResourceNumberRequirementRepresentation.class, name = "ResourceNumberRequirementRepresentation"),
})
public abstract class ClientLeaderCardRequirementRepresentation extends ClientRepresentation {

    public abstract String getDescription();
}
