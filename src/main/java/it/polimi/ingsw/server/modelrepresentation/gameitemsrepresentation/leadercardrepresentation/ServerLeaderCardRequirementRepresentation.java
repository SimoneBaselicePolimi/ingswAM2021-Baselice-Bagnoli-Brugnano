package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.storagerepresentation.ServerResourceNumberRequirementRepresentation;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "requirementType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ServerDevelopmentCardColourAndLevelRequirementRepresentation.class, name = "DevelopmentCardColourAndLevelRequirementRepresentation"),
    @JsonSubTypes.Type(value = ServerDevelopmentCardColourRequirementRepresentation.class, name = "DevelopmentCardColourRequirementRepresentation"),
    @JsonSubTypes.Type(value = ServerResourceNumberRequirementRepresentation.class, name = "ResourceNumberRequirementRepresentation"),
})
public abstract class ServerLeaderCardRequirementRepresentation extends ServerRepresentation {

}
