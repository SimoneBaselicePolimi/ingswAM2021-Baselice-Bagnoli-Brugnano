package it.polimi.ingsw.localization;

import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientMarbleColourRepresentation;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
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
                "- %s %s\n",
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

    public static String getNumberAndLevelOfDevCardsAsCompactString(
        DevelopmentCardColour cardColour,
        Map<DevelopmentCardLevel, Long> devCards
    ){
        StringBuilder s = new StringBuilder();
        for (DevelopmentCardLevel cardLevel : devCards.keySet()) {
            if(!s.toString().isEmpty())
                s.append(", ");
            s.append(String.format(
                "%s %s %s %s %s",
                devCards.get(cardLevel),
                devCards.get(cardLevel) == 1 ?
                    Localization.getLocalizationInstance().getString(
                        "gameHistory.faithPath.singlePlayer.discardCards.cards.singular"
                    ) : Localization.getLocalizationInstance().getString(
                    "gameHistory.faithPath.singlePlayer.discardCards.cards.plural"
                ),
                devCards.get(cardLevel) == 1 ?
                    cardColour.getColourNameLocalizedSingular() : cardColour.getColourNameLocalizedPlural(),
                Localization.getLocalizationInstance().getString(
                    "gameHistory.faithPath.singlePlayer.discardCards.level"
                ),
                cardLevel.toValue()
            ));
        }
        return s.toString();
    }

}
