package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation;

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
        String itemID,
        ResourceType resourceTypeToSubstitute
    ) {
        super(itemID);
        this.resourceTypeToSubstitute = resourceTypeToSubstitute;
    }
}
