package it.polimi.ingsw.client.modelrepresentation;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientRegisteredIdentifiableItemRepresentation;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;

public class ClientPlayerRepresentation extends ClientRegisteredIdentifiableItemRepresentation {

    protected ClientPlayerRepresentation(String itemID, GameItemsManager gameItemsManager) {
        super(itemID, gameItemsManager);
    }

}
