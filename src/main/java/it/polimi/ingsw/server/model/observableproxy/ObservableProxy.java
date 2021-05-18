package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.Set;

public abstract class ObservableProxy<I> {

    protected I imp;
    protected GameManager gameManager;

    public ObservableProxy(I imp, GameManager gameManager) {
        this.imp = imp;
        this.gameManager = gameManager;
        gameManager.registerObservableProxy(this);
    }

    public abstract Set<ServerGameUpdate> getUpdates() throws ResourceStorageRuleViolationException, NotEnoughResourcesException;



}
