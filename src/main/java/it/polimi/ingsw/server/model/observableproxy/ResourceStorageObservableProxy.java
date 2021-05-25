package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerResourceStorageUpdate;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRule;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.server.modelrepresentation.storagerepresentation.ServerResourceStorageRepresentation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResourceStorageObservableProxy extends ObservableProxy<ResourceStorage> implements ResourceStorage {

    protected boolean haveResourcesInStorageChanged = false;

    public ResourceStorageObservableProxy(ResourceStorage imp, GameManager gameManager) {
        super(imp, gameManager);
    }

    @Override
    public String getItemID() {
        return imp.getItemID();
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
        haveResourcesInStorageChanged = true;
        imp.addResources(newResources);
    }

    @Override
    public Map<ResourceType, Integer> removeResources(Map<ResourceType, Integer> resourcesToRemove) throws NotEnoughResourcesException {
        haveResourcesInStorageChanged = true;
        return imp.removeResources(resourcesToRemove);
    }

    @Override
    public Map<ResourceType, Integer> peekResources() {
        return imp.peekResources();
    }

    @Override
    public List<ResourceStorageRule> getRules() {
        return imp.getRules();
    }

    @Override
    public Set<ServerGameUpdate> getUpdates() {
        if(haveResourcesInStorageChanged) {
            haveResourcesInStorageChanged = false;
            return Set.of(new ServerResourceStorageUpdate(this, imp.peekResources()));
        }
        else
            return new HashSet<>();
    }

    @Override
    public ServerResourceStorageRepresentation getServerRepresentation() {
        return imp.getServerRepresentation();
    }

    @Override
    public ServerResourceStorageRepresentation getServerRepresentationForPlayer(Player player) {
        return imp.getServerRepresentationForPlayer(player);
    }

}
