package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.observableproxy.ResourceStorageObservableProxy;

public class ObservableResourcesStorageBuilder extends ResourceStorageBuilder {

    protected GameManager gameManager;

    public ObservableResourcesStorageBuilder(GameItemsManager gameItemsManager, GameManager gameManager) {
        super(gameItemsManager);
        this.gameManager = gameManager;
    }

    @Override
    public ResourceStorage createResourceStorage(String storageID) {
        return new ResourceStorageObservableProxy(super.createResourceStorage(storageID), gameManager);
    }
}
