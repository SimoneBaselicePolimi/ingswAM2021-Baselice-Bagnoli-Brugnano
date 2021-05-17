package it.polimi.ingsw.server.modelrepresentation;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientRegisteredIdentifiableItemRepresentation;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerRegisteredIdentifiableItemRepresentation;

public class ServerPlayerRepresentation extends ServerRegisteredIdentifiableItemRepresentation {

    protected ServerPlayerRepresentation(String itemID, GameItemsManager gameItemsManager) {
        super(itemID, gameItemsManager);
    }

}
