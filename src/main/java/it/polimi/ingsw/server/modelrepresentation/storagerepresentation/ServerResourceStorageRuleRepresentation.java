package it.polimi.ingsw.server.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "ruleType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ServerDifferentResourceTypesInDifferentStoragesRuleRepresentation.class, name = "DifferentResourceTypesInDifferentStoragesRuleRepresentation"),
    @JsonSubTypes.Type(value = ServerMaxResourceNumberRuleRepresentation.class, name = "MaxResourceNumberRuleRepresentation"),
    @JsonSubTypes.Type(value = ServerSameResourceTypeRuleRepresentation.class, name = "SameResourceTypeRuleRepresentation"),
    @JsonSubTypes.Type(value = ServerSpecificResourceTypeRuleRepresentation.class, name = "SpecificResourceTypeRuleRepresentation")
})
public abstract class ServerResourceStorageRuleRepresentation extends ServerRepresentation {
}
