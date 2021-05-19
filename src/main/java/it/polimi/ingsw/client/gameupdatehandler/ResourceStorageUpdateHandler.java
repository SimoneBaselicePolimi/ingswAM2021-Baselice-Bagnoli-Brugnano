package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientResourceStorageUpdate;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;


public class ResourceStorageUpdateHandler extends GameUpdateHandler<ClientResourceStorageUpdate>{

    @Override
    public void handleGameUpdate(ClientResourceStorageUpdate update, ClientGameContextRepresentation gameContextRepresentation) {
        update.storage.setResources(update.resourcesInStorage);
    }
}

