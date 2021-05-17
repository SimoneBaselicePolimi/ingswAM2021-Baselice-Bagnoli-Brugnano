package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientShuffledDevelopmentCardDeckOnTableUpdate;
import it.polimi.ingsw.client.model.CoveredCardsDeckRepresentation;
import it.polimi.ingsw.client.model.DevelopmentCardRepresentation;
import it.polimi.ingsw.client.model.GameContextRepresentation;

public class ShuffledDevelopmentCardDeckOnTableUpdateHandler extends GameUpdateHandler<ClientShuffledDevelopmentCardDeckOnTableUpdate>{

    @Override
    public void handleGameUpdate(ClientShuffledDevelopmentCardDeckOnTableUpdate update, GameContextRepresentation gameContextRepresentation) {
        CoveredCardsDeckRepresentation<DevelopmentCardRepresentation> deckToUpdate = update.deck;
        deckToUpdate.setCardOnTop(update.cardOnTop);
        deckToUpdate.setNumberOfCardsInDeck(update.numberOfCardsInDeck);
    }
}
