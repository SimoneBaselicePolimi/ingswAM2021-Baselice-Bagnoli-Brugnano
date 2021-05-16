package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.model.GameContextRepresentation;
import it.polimi.ingsw.client.model.MarketRepresentation;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerMarketUpdate;

public class MarketUpdateHandler extends GameUpdateHandler<ServerMarketUpdate> {

    @Override
    public void handleGameUpdate(ServerMarketUpdate update, GameContextRepresentation gameContextRepresentation) {
        MarketRepresentation market; // gameContextRepresentation ...
        market.setMatrix(update.matrix);
        market.setOutMarble(update.outMarble);
    }
}
