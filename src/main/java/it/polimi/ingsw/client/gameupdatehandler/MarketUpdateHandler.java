package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientMarketUpdate;
import it.polimi.ingsw.client.model.GameContextRepresentation;
import it.polimi.ingsw.client.model.MarketRepresentation;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerMarketUpdate;

public class MarketUpdateHandler extends GameUpdateHandler<ClientMarketUpdate> {

    @Override
    public void handleGameUpdate(ClientMarketUpdate update, GameContextRepresentation gameContextRepresentation) {
        MarketRepresentation market = gameContextRepresentation.getMarket();
        market.setMatrix(update.matrix);
        market.setOutMarble(update.outMarble);
    }
}
