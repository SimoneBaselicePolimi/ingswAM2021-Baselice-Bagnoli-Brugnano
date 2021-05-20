package it.polimi.ingsw.localization;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class LocalizationUtils {

    private final static Map<ResourceType, String> RESOURCELOC = Map.of(
        ResourceType.COINS, "resources.coins",
        ResourceType.SERVANTS, "resources.servants",
        ResourceType.SHIELDS, "resources.shields",
        ResourceType.STONES, "resources.stones"
    );

    public static String getResourcesListAsString(Map<ResourceType, Integer> resources){
        StringBuilder s = new StringBuilder();
        for (ResourceType resourceType : resources.keySet()) {
            String resourceNamePlaceholder = resources.get(resourceType) == 1 ?
                RESOURCELOC.get(resourceType)+".singular" : RESOURCELOC.get(resourceType)+".plural";
            s.append(String.format(
                "- %s %s",
                resources.get(resourceType),
                Localization.getLocalizationInstance().getString(resourceNamePlaceholder)
            ));
        }
        return s.toString();
    }
}
