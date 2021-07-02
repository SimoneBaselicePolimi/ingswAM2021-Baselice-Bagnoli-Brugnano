package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientShuffledDevelopmentCardDeckOnTableUpdate;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCoveredCardsDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;

public class ShuffledDevelopmentCardDeckOnTableUpdateHandler extends GameUpdateHandler<ClientShuffledDevelopmentCardDeckOnTableUpdate>{

    @Override
    public void handleGameUpdate(ClientShuffledDevelopmentCardDeckOnTableUpdate update, ClientManager clientManager) {
        ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation> deckToUpdate = update.deck;
        deckToUpdate.setCardOnTop(update.cardOnTop);
        deckToUpdate.setNumberOfCardsInDeck(update.numberOfCardsInDeck);
    }
}
