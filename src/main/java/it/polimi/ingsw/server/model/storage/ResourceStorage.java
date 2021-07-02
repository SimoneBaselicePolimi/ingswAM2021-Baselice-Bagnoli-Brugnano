package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.modelrepresentation.storagerepresentation.ServerResourceStorageRepresentation;

import java.util.List;
import java.util.Map;

public interface ResourceStorage extends IdentifiableItem, Representable <ServerResourceStorageRepresentation> {

    /**
     * Method to add new resources to the storage
     * @param newResources resources to add
     * @return true if it is possible to add new resources to the storage
     */
    boolean canAddResources (Map<ResourceType, Integer> newResources);

    /**
     * Method to remove resources from the storage
     * @param resourcesToRemove resources to remove
     * @return true if it is possible to remove resources from the storage (there are enough resources to remove)
     */
    boolean canRemoveResources(Map<ResourceType, Integer> resourcesToRemove);

    /**
     * Method to add new resources (type and number) in the storage
     * @param newResources Map of resources that the method adds to the storage
     * @throws ResourceStorageRuleViolationException if new resources don't respect rules of the storage
     */
    void addResources(Map<ResourceType, Integer> newResources) throws ResourceStorageRuleViolationException;

    /**
     * Method to remove some resources (type and number) from the storage
     * @param resourcesToRemove Map of resources that the method removes from the storage
     * @return resourcesToRemove Map of resources that the method removes from the storage
     * @throws NotEnoughResourcesException if there aren't enough resources to remove
     */
    Map<ResourceType, Integer> removeResources(Map<ResourceType, Integer> resourcesToRemove)
            throws NotEnoughResourcesException;

    /**
     * Method to  Method to peek resources present in the storage
     * @return Type and number of resources in the storage
     */
    Map<ResourceType, Integer> peekResources();

    List<ResourceStorageRule> getRules();

}
