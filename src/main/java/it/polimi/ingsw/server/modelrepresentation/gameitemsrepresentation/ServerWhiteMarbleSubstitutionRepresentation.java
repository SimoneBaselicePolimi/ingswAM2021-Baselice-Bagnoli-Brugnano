package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

public class ServerWhiteMarbleSubstitutionRepresentation extends ServerRegisteredIdentifiableItemRepresentation {

    /**
     * Resource type the Player can substitute to a White Marble taken from the Market
     */
    public final ResourceType resourceTypeToSubstitute;

    /**
     * WhiteMarbleSubstitutionRepresentation constructor.
     * @param resourceTypeToSubstitute resource type to substitute to a White Marble
     */
    public ServerWhiteMarbleSubstitutionRepresentation(
        @JsonProperty("itemID") String itemID,
        @JsonProperty("resourceTypeToSubstitute") ResourceType resourceTypeToSubstitute
    ) {
        super(itemID);
        this.resourceTypeToSubstitute = resourceTypeToSubstitute;
    }
}
