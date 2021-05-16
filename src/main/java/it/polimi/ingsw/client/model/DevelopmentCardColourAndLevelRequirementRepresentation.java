package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

public class DevelopmentCardColourAndLevelRequirementRepresentation extends LeaderCardRequirementRepresentation{

    DevelopmentCardColour cardColour;
    DevelopmentCardLevel cardLevel;
    int numberOfCards;

    /**
     * DevelopmentCardColourAndLevelRequirementRepresentation constructor
     * @param cardColour colour of development cards required
     * @param cardLevel level of development cards required
     * @param numberOfCards number of development cards required
     */
    public DevelopmentCardColourAndLevelRequirementRepresentation(
        DevelopmentCardColour cardColour,
        DevelopmentCardLevel cardLevel,
        int numberOfCards
    ) {
        this.cardColour=cardColour;
        this.cardLevel=cardLevel;
        this.numberOfCards=numberOfCards;
    }

    public DevelopmentCardColour getCardColour() {
        return cardColour;
    }

    public DevelopmentCardLevel getCardLevel() {
        return cardLevel;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }
}
