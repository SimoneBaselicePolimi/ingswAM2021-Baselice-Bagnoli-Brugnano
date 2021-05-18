package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.*;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResourceStorageObservableProxy extends ObservableProxy<ResourceStorage> implements ResourceStorage {

    protected boolean hasThePlayerAddResources = false;
    protected boolean hasThePlayerRemoveResources = false;
    Map<ResourceType, Integer> resourcesToAdd;
    Map<ResourceType, Integer> resourcesToRemove;

    public ResourceStorageObservableProxy(ResourceStorage imp, GameManager gameManager) {
        super(imp, gameManager);
    }

    @Override
    public String getItemId() {
        return imp.getItemId();
    }

    @Override
    public boolean canAddResources(Map<ResourceType, Integer> newResources) {
        return imp.canAddResources(newResources);
    }

    @Override
    public boolean canRemoveResources(Map<ResourceType, Integer> resourcesToRemove) {
        return imp.canRemoveResources(resourcesToRemove);
    }

    @Override
    public void addResources(Map<ResourceType, Integer> newResources) throws ResourceStorageRuleViolationException {
        this.resourcesToAdd = newResources;
        hasThePlayerAddResources = true;
        imp.addResources(newResources);
    }

    @Override
    public Map<ResourceType, Integer> removeResources(Map<ResourceType, Integer> resourcesToRemove) throws NotEnoughResourcesException {
        this.resourcesToRemove = resourcesToRemove;
        hasThePlayerRemoveResources = true;
        return imp.removeResources(resourcesToRemove);
    }

    @Override
    public Map<ResourceType, Integer> peekResources() {
        return imp.peekResources();
    }

    @Override
    public Set<ServerGameUpdate> getUpdates() throws ResourceStorageRuleViolationException, NotEnoughResourcesException {
        Set<ServerGameUpdate> updates = new HashSet<>();
        if(hasThePlayerAddResources) {
            hasThePlayerAddResources = false;
            Map<ResourceType, Integer> allResourcesAfterAdd = new HashMap<>();
            allResourcesAfterAdd.putAll(imp.peekResources());
            allResourcesAfterAdd.putAll(resourcesToAdd);
            updates.add(new ServerResourceStorageUpdate(imp, allResourcesAfterAdd));
        }
        if (hasThePlayerRemoveResources){
            hasThePlayerRemoveResources = false;
            Map<ResourceType, Integer> allResourcesAfterRemove = new HashMap<>();
            allResourcesAfterRemove.putAll(imp.peekResources());
            allResourcesAfterRemove.putAll(imp.removeResources(resourcesToRemove));
            updates.add(new ServerResourceStorageUpdate(imp, allResourcesAfterRemove));
        }
        return updates;
    }
}
