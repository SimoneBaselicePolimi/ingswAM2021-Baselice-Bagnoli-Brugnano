package it.polimi.ingsw.localization;

import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientMarbleColourRepresentation;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.Colour;

import java.util.ArrayList;
import java.util.List;
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

    public static List<FormattedChar> getMarblesAsListOfFormattedChar(Map<ClientMarbleColourRepresentation, Integer> marbles){
        List<FormattedChar> marblesDescription = new ArrayList<>();
        for (ClientMarbleColourRepresentation marble : marbles.keySet()) {
            marblesDescription.add(new FormattedChar('\n'));
            marblesDescription.add(new FormattedChar(' '));
            marblesDescription.addAll(FormattedChar.convertStringToFormattedCharList(
                "   ",
                Colour.WHITE,
                marble.getMarbleColour().get(0)
            ));
            marblesDescription.add(new FormattedChar(' '));
            marblesDescription.addAll(FormattedChar.convertStringToFormattedCharList(
                "X " + marbles.get(marble),
                Colour.WHITE,
                Colour.BLACK
            ));
        }
        return marblesDescription;
    }

}
