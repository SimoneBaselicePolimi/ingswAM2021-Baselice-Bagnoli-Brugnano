package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation;

import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;

public class ServerDevelopmentCardColourRequirementRepresentation extends ServerLeaderCardRequirementRepresentation {

    public final DevelopmentCardColour cardColour;
    public final int numberOfCards;

    /**
     * DevelopmentCardColourRequirementRepresentation constructor
     * @param cardColour colour of development cards required
     * @param numberOfCards number of development cards required
     */
    public ServerDevelopmentCardColourRequirementRepresentation(DevelopmentCardColour cardColour, int numberOfCards) {
        this.cardColour = cardColour;
        this.numberOfCards = numberOfCards;
    }
}
