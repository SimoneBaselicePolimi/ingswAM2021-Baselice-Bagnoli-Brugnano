package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.model.GameContextRepresentation;
import it.polimi.ingsw.client.model.ResourceStorageRepresentation;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerMarketUpdate;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

public class ResourceStorageUpdateHandler extends GameUpdateHandler<ServerMarketUpdate>{
    @Override
    public void handleGameUpdate(ServerMarketUpdate update, GameContextRepresentation gameContextRepresentation) {
        ResourceStorageRepresentation resourceStorage = gameContextRepresentation.getPlayerContexts()
    }
}
