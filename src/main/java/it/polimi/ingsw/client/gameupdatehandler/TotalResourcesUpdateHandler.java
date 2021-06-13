package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientTotalResourcesUpdate;

public class TotalResourcesUpdateHandler extends GameUpdateHandler<ClientTotalResourcesUpdate>{

    @Override
    public void handleGameUpdate(ClientTotalResourcesUpdate update, ClientManager clientManager) {
        clientManager.getGameContextRepresentation().getPlayerContext(update.player)
            .setTotalResourcesOwnedByThePlayer(update.totalResources);
    }
}
