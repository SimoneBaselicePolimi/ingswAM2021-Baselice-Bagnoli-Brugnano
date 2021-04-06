package it.polimi.ingsw.server.model.gameitems;

import java.util.Map;

public class ResourceUtils {

    /**
     * This method checks if the resources given as first parameter `resourcesA` are a subset of the resources given as
     * second parameter `resourcesB`.
     * <p>
     * areResourcesAContainedInB(A, B) <=> FOREACH tuple (resourceType, numberOfResourcesA) in A EXISTS tuple
     * (resourceType, numberOfResourcesB) in B AND number of numberOfResourcesA <= numberOfResourcesB
     * <p>
     * As an example, this method may be used to check if a player has enough resources to buy a certain card:
     * areResourcesAContainedInB(cardCost, playerResources)
     * @param resourcesA A
     * @param resourcesB B
     * @return returns true if A subset of B
     */
    public static boolean areResourcesAContainedInB(
            Map<ResourceType, Integer> resourcesA,
            Map<ResourceType, Integer> resourcesB
    ) {
        return resourcesA.entrySet().stream().allMatch(entryA ->
                resourcesB.containsKey(entryA.getKey()) && entryA.getValue() <= resourcesB.get(entryA.getKey())
        );
    }

}
