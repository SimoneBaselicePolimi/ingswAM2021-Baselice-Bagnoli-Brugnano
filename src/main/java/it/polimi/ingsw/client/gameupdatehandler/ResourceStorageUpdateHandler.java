package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientResourceStorageUpdate;


public class ResourceStorageUpdateHandler extends GameUpdateHandler<ClientResourceStorageUpdate>{

    @Override
    public void handleGameUpdate(ClientResourceStorageUpdate update, ClientManager clientManager) {
        update.storage.setResources(update.resourcesInStorage);
    }
}

