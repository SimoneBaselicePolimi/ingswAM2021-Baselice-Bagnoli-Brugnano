package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientResourceStorageUpdate;
import it.polimi.ingsw.client.model.GameContextRepresentation;


public class ResourceStorageUpdateHandler extends GameUpdateHandler<ClientResourceStorageUpdate>{

    @Override
    public void handleGameUpdate(ClientResourceStorageUpdate update, GameContextRepresentation gameContextRepresentation) {
        update.storage.setResources(update.resourcesInStorage);
    }
}

