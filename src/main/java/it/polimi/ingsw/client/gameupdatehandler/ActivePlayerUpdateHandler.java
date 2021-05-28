package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientActivePlayerUpdate;

public class ActivePlayerUpdateHandler extends GameUpdateHandler<ClientActivePlayerUpdate> {

    @Override
    public void handleGameUpdate(ClientActivePlayerUpdate update, ClientManager clientManager) {
        clientManager.getGameContextRepresentation().setActivePlayer(update.newActivePlayer);
    }
}
