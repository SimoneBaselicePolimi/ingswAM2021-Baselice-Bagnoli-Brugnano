package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Optional;

public class ServerMarbleColourRepresentation extends ServerRegisteredIdentifiableItemRepresentation {

    /**
     * Optional type of resource obtainable with this Marble
     */
    public final Optional<ResourceType> resourceType;

    /**
     * Number of faith points given to the Player who owns this Marble
     */
    public final int faithPoints;

    /**
     * Marble that can be transformed into a generic type of resource by activating a special Leader card power
     */
    public final boolean isSpecialMarble;

    /**
     * MarbleColourRepresentation constructor.
     * @param itemID ID which identifies this specific Marble
     * @param resourceType optional type of resource obtainable with this Marble
     * @param faithPoints number of faith points obtainable by this Marble
     * @param isSpecialMarble boolean which states if this Marble can be transformed into a generic type of resource
     * by activating a special Leader card power
     */
    public ServerMarbleColourRepresentation(
        @JsonProperty("itemID") String itemID,
        @JsonProperty("resourceType") Optional<ResourceType> resourceType,
        @JsonProperty("faithPoints") int faithPoints,
        @JsonProperty("isSpecialMarble") boolean isSpecialMarble
    ) {
        super(itemID);
        this.resourceType = resourceType;
        this.faithPoints = faithPoints;
        this.isSpecialMarble = isSpecialMarble;
    }
}
