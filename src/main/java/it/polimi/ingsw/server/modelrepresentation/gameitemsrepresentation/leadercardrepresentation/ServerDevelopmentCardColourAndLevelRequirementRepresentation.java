package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation;

import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

public class ServerDevelopmentCardColourAndLevelRequirementRepresentation extends ServerLeaderCardRequirementRepresentation {

    DevelopmentCardColour cardColour;
    DevelopmentCardLevel cardLevel;
    int numberOfCards;

    /**
     * DevelopmentCardColourAndLevelRequirementRepresentation constructor
     * @param cardColour colour of development cards required
     * @param cardLevel level of development cards required
     * @param numberOfCards number of development cards required
     */
    public ServerDevelopmentCardColourAndLevelRequirementRepresentation(
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
