package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientDevelopmentCardsTableUpdate;
import it.polimi.ingsw.client.model.CardDeckRepresentation;
import it.polimi.ingsw.client.model.DevelopmentCardsTableRepresentation;
import it.polimi.ingsw.client.model.GameContextRepresentation;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerDevelopmentCardsTableUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerMarketUpdate;

public class ShuffledDevelopmentCardDeckOnTableUpdateHandler extends GameUpdateHandler<ClientDevelopmentCardsTableUpdate>{
    //TODO Stesso di ClientDevelopmentCardsTableUpdate? Quale utilizziamo?
    @Override
    public void handleGameUpdate(ClientDevelopmentCardsTableUpdate update, GameContextRepresentation gameContextRepresentation) {
        DevelopmentCardsTableRepresentation cardDeck = gameContextRepresentation.getDevelopmentCardsTable();
        cardDeck.setCards(update.developmentCardsOnTop);
    }
}
