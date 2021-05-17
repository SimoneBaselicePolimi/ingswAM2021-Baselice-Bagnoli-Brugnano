package it.polimi.ingsw.server.modelrepresentation;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientRegisteredIdentifiableItemRepresentation;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;

public class ServerPlayerRepresentation extends ClientRegisteredIdentifiableItemRepresentation {

    protected ServerPlayerRepresentation(String itemID, GameItemsManager gameItemsManager) {
        super(itemID, gameItemsManager);
    }

}
