package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientResourceStorageUpdate;
import it.polimi.ingsw.client.model.GameContextRepresentation;
import it.polimi.ingsw.client.model.ResourceStorageRepresentation;


public class ResourceStorageUpdateHandler extends GameUpdateHandler<ClientResourceStorageUpdate>{
    //TODO l'Update ClientResourceStorageUpdate ha una Map<ResourceType, Integer>. Non so che Storage modificare!!! (shelves? infinite chest?..)
    @Override
    public void handleGameUpdate(ClientResourceStorageUpdate update, GameContextRepresentation gameContextRepresentation) {

        ResourceStorageRepresentation resourceStorage = gameContextRepresentation.getPlayerContexts();
        resourceStorage.setResources(update.resourcesInStorage);
    }
}

