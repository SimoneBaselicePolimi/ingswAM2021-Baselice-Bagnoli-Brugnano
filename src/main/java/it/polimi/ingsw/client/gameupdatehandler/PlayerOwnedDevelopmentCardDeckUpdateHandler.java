package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientPlayerOwnedDevelopmentCardDeckUpdate;

public class PlayerOwnedDevelopmentCardDeckUpdateHandler extends GameUpdateHandler<ClientPlayerOwnedDevelopmentCardDeckUpdate> {

    @Override
    public void handleGameUpdate(
        ClientPlayerOwnedDevelopmentCardDeckUpdate update,
        ClientManager clientManager
    ) {
        update.deck.setCardDeck(update.developmentCardsDeck);
    }
}