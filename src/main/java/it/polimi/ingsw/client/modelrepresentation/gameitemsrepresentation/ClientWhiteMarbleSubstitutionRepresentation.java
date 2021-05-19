package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

public class ClientWhiteMarbleSubstitutionRepresentation extends ClientRegisteredIdentifiableItemRepresentation {

    /**
     * Resource type the Player can substitute to a White Marble taken from the Market
     */
    private final ResourceType resourceTypeToSubstitute;

    /**
     * WhiteMarbleSubstitutionRepresentation constructor.
     * @param resourceTypeToSubstitute resource type to substitute to a White Marble
     */
    public ClientWhiteMarbleSubstitutionRepresentation(
        String itemID,
        GameItemsManager gameItemsManager,
        ResourceType resourceTypeToSubstitute
    ) {
        super(itemID, gameItemsManager);
        this.resourceTypeToSubstitute = resourceTypeToSubstitute;
    }
}
