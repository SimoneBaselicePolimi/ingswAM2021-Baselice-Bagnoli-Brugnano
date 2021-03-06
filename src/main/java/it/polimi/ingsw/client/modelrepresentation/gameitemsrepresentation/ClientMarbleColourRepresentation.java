package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.Colour;

import java.util.List;
import java.util.Optional;

public class ClientMarbleColourRepresentation extends ClientRegisteredIdentifiableItemRepresentation {

    /**
     * Optional type of resource obtainable with this Marble
     */
    private final Optional<ResourceType> resourceType;

    /**
     * Number of faith points given to the Player who owns this Marble
     */
    private final int faithPoints;

    /**
     * Marble that can be transformed into a generic type of resource by activating a special Leader card power
     */
    private final boolean isSpecialMarble;

    /**
     * Marble colour, specified as a list (in case of multi-coloured marbles) of RGB colour codes
     */
    private final List<Colour> marbleColour;

    /**
     * MarbleColourRepresentation constructor.
     * @param itemID ID which identifies this specific Marble
     * @param gameItemsManager a reference to gameItemsManager is needed to register the new MarbleColour object
     * (see {@link RegisteredIdentifiableItem})
     * @param resourceType optional type of resource obtainable with this Marble
     * @param faithPoints number of faith points obtainable by this Marble
     * @param isSpecialMarble boolean which states if this Marble can be transformed into a generic type of resource
     * @param marbleColour marble colour, specified as a list (in case of multi-coloured marbles) of RGB colour codes
     */
    public ClientMarbleColourRepresentation(
        @JsonProperty("itemID") String itemID,
        @JacksonInject("gameItemsManager") GameItemsManager gameItemsManager,
        @JsonProperty("resourceType") Optional<ResourceType> resourceType,
        @JsonProperty("faithPoints") int faithPoints,
        @JsonProperty("isSpecialMarble") boolean isSpecialMarble,
        @JsonProperty("marbleColour") List<Colour> marbleColour
    ) {
        super(itemID, gameItemsManager);
        this.resourceType = resourceType;
        this.faithPoints = faithPoints;
        this.isSpecialMarble = isSpecialMarble;
        this.marbleColour = marbleColour;
    }

    public Optional<ResourceType> getResourceType() {
        return resourceType;
    }

    public int getFaithPoints() {
        return faithPoints;
    }

    public boolean isSpecialMarble() {
        return isSpecialMarble;
    }

    public List<Colour> getMarbleColour() {
        return marbleColour;
    }

}
