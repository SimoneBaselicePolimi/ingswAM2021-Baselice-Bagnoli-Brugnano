package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientPurchasableDevelopmentCardsUpdate;

public class PurchasableDevelopmentCardsUpdateHandler extends GameUpdateHandler<ClientPurchasableDevelopmentCardsUpdate>{

    @Override
    public void handleGameUpdate(ClientPurchasableDevelopmentCardsUpdate update, ClientManager clientManager) {
        clientManager.getGameContextRepresentation().getDevelopmentCardsTable().setPurchasableCards(update.purchasableDevelopmentCards);
    }

}
