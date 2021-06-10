package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientTempStarResourcesUpdate;

public class TempStarResourcesUpdateHandler extends GameUpdateHandler<ClientTempStarResourcesUpdate> {

    @Override
    public void handleGameUpdate(ClientTempStarResourcesUpdate update, ClientManager clientManager) {
        clientManager.getGameContextRepresentation().getPlayerContext(update.player)
            .setTempStarResources(update.tempStarResources);
    }
}
