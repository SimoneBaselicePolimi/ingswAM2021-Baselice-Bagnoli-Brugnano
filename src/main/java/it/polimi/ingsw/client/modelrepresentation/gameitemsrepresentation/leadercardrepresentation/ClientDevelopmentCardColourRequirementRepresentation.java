package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation;

import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;

public class ClientDevelopmentCardColourRequirementRepresentation extends ClientLeaderCardRequirementRepresentation {
    DevelopmentCardColour cardColour;
    int numberOfCards;

    /**
     * DevelopmentCardColourRequirementRepresentation constructor
     * @param cardColour colour of development cards required
     * @param numberOfCards number of development cards required
     */
    public ClientDevelopmentCardColourRequirementRepresentation(DevelopmentCardColour cardColour, int numberOfCards) {
        this.cardColour = cardColour;
        this.numberOfCards = numberOfCards;
    }

    public DevelopmentCardColour getCardColour() {
        return cardColour;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }
}
