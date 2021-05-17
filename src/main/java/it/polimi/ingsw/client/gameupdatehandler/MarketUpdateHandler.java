package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientMarketUpdate;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.marketrepresentation.ClientMarketRepresentation;

public class MarketUpdateHandler extends GameUpdateHandler<ClientMarketUpdate> {

    @Override
    public void handleGameUpdate(ClientMarketUpdate update, ClientGameContextRepresentation gameContextRepresentation) {
        ClientMarketRepresentation market = gameContextRepresentation.getMarket();
        market.setMatrix(update.matrix);
        market.setOutMarble(update.outMarble);
    }
}
