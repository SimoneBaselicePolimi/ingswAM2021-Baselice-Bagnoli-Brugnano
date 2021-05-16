package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.model.CardDeckRepresentation;
import it.polimi.ingsw.client.model.DevelopmentCardsTableRepresentation;
import it.polimi.ingsw.client.model.GameContextRepresentation;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerMarketUpdate;

public class ShuffledDevelopmentCardDeckOnTableUpdateHandler extends GameUpdateHandler<ServerMarketUpdate>{
    @Override
    public void handleGameUpdate(ServerMarketUpdate update, GameContextRepresentation gameContextRepresentation) {
        DevelopmentCardsTableRepresentation cardDeck = gameContextRepresentation.getDevelopmentCardsTable();
        cardDeck.setCards(update.);
        // market.setMatrix(update.matrix);
        //        market.setOutMarble(update.outMarble);

    }
}
