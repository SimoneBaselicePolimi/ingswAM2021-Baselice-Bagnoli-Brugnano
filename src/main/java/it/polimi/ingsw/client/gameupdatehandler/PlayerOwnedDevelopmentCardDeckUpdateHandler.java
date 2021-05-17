package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientPlayerOwnedDevelopmentCardDeckUpdate;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;

public class PlayerOwnedDevelopmentCardDeckUpdateHandler extends GameUpdateHandler<ClientPlayerOwnedDevelopmentCardDeckUpdate> {

    @Override
    public void handleGameUpdate(
        ClientPlayerOwnedDevelopmentCardDeckUpdate update,
        ClientGameContextRepresentation gameContextRepresentation
    ) {
        update.deck.setCardDeck(update.developmentCardsDeck);
    }
}