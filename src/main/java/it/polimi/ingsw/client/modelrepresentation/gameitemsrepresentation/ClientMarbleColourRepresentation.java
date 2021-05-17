package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

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
     * MarbleColourRepresentation constructor.
     * @param itemID ID which identifies this specific Marble
     * @param gameItemsManager a reference to gameItemsManager is needed to register the new MarbleColour object
     * (see {@link RegisteredIdentifiableItem})
     * @param resourceType optional type of resource obtainable with this Marble
     * @param faithPoints number of faith points obtainable by this Marble
     * @param isSpecialMarble boolean which states if this Marble can be transformed into a generic type of resource
     * by activating a special Leader card power
     */
    public ClientMarbleColourRepresentation(
        String itemID,
        GameItemsManager gameItemsManager,
        Optional<ResourceType> resourceType,
        int faithPoints,
        boolean isSpecialMarble
    ) {
        super(itemID, gameItemsManager);
        this.resourceType = resourceType;
        this.faithPoints = faithPoints;
        this.isSpecialMarble = isSpecialMarble;
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
}
