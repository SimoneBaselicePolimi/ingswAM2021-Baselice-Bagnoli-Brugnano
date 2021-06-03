package it.polimi.ingsw.localization;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class LocalizationUtils {

    public static String getResourcesListAsString(Map<ResourceType, Integer> resources){
        StringBuilder s = new StringBuilder();
        for (ResourceType resourceType : resources.keySet()) {
            String resourceName = resources.get(resourceType) == 1 ?
                resourceType.getLocalizedNameSingular() : resourceType.getLocalizedNamePlural();
            s.append(String.format(
                "- %s %s",
                resources.get(resourceType),
                resourceName
            ));
        }
        return s.toString();
    }

    public static String getResourcesListAsCompactString(Map<ResourceType, Integer> resources){
        StringBuilder s = new StringBuilder();
        for (ResourceType resourceType : resources.keySet()) {
            if(!s.toString().isEmpty())
                s.append(", ");
            String resourceName = resources.get(resourceType) == 1 ?
                resourceType.getLocalizedNameSingular() : resourceType.getLocalizedNamePlural();
            s.append(String.format(
                "%s %s",
                resources.get(resourceType),
                resourceName
            ));
        }
        return s.toString();
    }

}
