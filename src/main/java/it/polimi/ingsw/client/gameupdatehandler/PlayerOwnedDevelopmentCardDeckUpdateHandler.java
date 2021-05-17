package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientPlayerOwnedDevelopmentCardDeckUpdate;
import it.polimi.ingsw.client.model.GameContextRepresentation;

public class PlayerOwnedDevelopmentCardDeckUpdateHandler extends GameUpdateHandler<ClientPlayerOwnedDevelopmentCardDeckUpdate> {

    @Override
    public void handleGameUpdate(
        ClientPlayerOwnedDevelopmentCardDeckUpdate update,
        GameContextRepresentation gameContextRepresentation
    ) {
        update.deck.setCardDeck(update.developmentCardsDeck);
    }
}