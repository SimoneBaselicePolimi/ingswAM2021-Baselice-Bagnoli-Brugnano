package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientPopeCardsUpdate;

public class PopeCardsUpdateHandler extends GameUpdateHandler<ClientPopeCardsUpdate>{
    @Override
    public void handleGameUpdate(ClientPopeCardsUpdate update, ClientManager clientManager) {
        clientManager.getGameContextRepresentation().getFaithPath().setPopeFavorCards(update.faithPopeCardsStates);
    }
}
