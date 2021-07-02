package it.polimi.ingsw.client.modelrepresentation.storagerepresentation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;
import java.util.Optional;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "ruleType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ClientDifferentResourceTypesInDifferentStoragesRuleRepresentation.class, name = "DifferentResourceTypesInDifferentStoragesRuleRepresentation"),
    @JsonSubTypes.Type(value = ClientMaxResourceNumberRuleRepresentation.class, name = "MaxResourceNumberRuleRepresentation"),
    @JsonSubTypes.Type(value = ClientSameResourceTypeRuleRepresentation.class, name = "SameResourceTypeRuleRepresentation"),
    @JsonSubTypes.Type(value = ClientSpecificResourceTypeRuleRepresentation.class, name = "SpecificResourceTypeRuleRepresentation")
})
public abstract class ClientResourceStorageRuleRepresentation extends ClientRepresentation {

    public abstract Optional<String> getDescription();
    public abstract Optional<String> getErrorMessageIfPresent(ClientResourceStorageRepresentation storage, Map<ResourceType,Integer> newResources);

}
