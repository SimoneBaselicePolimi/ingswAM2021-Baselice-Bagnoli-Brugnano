package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;

public class ServerPlayerOwnedDevelopmentCardDeckRepresentation extends ServerCardDeckRepresentation<ServerDevelopmentCardRepresentation> {

    public ServerPlayerOwnedDevelopmentCardDeckRepresentation(String itemID, GameItemsManager gameItemsManager) {
        super(itemID, gameItemsManager);
    }
}
