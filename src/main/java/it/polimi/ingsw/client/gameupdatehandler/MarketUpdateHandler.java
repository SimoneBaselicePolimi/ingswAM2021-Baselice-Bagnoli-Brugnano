package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientMarketUpdate;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.marketrepresentation.ClientMarketRepresentation;

public class MarketUpdateHandler extends GameUpdateHandler<ClientMarketUpdate> {

    @Override
    public void handleGameUpdate(ClientMarketUpdate update, ClientManager clientManager) {
        ClientMarketRepresentation market = clientManager.getGameContextRepresentation().getMarket();
        market.setMatrix(update.matrix);
        market.setOutMarble(update.outMarble);
    }
}
