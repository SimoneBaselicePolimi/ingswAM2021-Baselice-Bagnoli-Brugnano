package it.polimi.ingsw.server.model.gameitems;

import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.min;

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

    //TODO JavaDoc (and Test?)
    public static Map<ResourceType, Integer> sum(
        Map<ResourceType, Integer> resourcesA,
        Map<ResourceType, Integer> resourcesB
    ) {
        return Stream.concat(resourcesA.entrySet().stream(), resourcesB.entrySet().stream())
            .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)));
    }

    //TODO JavaDoc (and Test?)
    public static Map<ResourceType, Integer> sum(
        Collection<Map<ResourceType, Integer>> resourcesToSum
    ) {
        return resourcesToSum.stream().reduce(new HashMap<>(), ResourceUtils::sum);
    }

    //TODO JavaDoc (and Test?)
    public static Map<ResourceType, Integer> difference(
        Map<ResourceType, Integer> resourcesA,
        Map<ResourceType, Integer> resourcesB
    ) throws IllegalArgumentException{

        Map<ResourceType, Integer> difference = new HashMap<>();
        for (ResourceType resourceType : resourcesB.keySet()) {
            if (!resourcesA.containsKey(resourceType))
                throw new IllegalArgumentException(String.format(
                    "Resources of type %s in B are not present in A",
                    resourceType
                ));
        }

        for (ResourceType resourceType : resourcesA.keySet()){
            if (resourcesA.get(resourceType) >= resourcesB.get(resourceType))
                difference.put(resourceType, resourcesA.get(resourceType) - resourcesB.get(resourceType));
            else
                throw new IllegalArgumentException(String.format(
                    "The number of resources of type %s in A is smaller than the number in B",
                    resourceType
                ));
        }
        return difference;
    }

    //TODO JavaDoc and test
    public static Map<ResourceType, Integer> intersection(
        Map<ResourceType, Integer> resourcesA,
        Map<ResourceType, Integer> resourcesB
    ) {
        return resourcesA.entrySet().stream()
            .filter(e -> resourcesB.containsKey(e.getKey()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> min(e.getValue(), resourcesB.get(e.getKey()))
            ));
    }

}
