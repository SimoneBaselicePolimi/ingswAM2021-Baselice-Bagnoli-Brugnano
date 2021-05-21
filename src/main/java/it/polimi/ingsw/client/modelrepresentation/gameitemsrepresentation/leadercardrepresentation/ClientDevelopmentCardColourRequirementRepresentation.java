package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;

public class ClientDevelopmentCardColourRequirementRepresentation extends ClientLeaderCardRequirementRepresentation {

    DevelopmentCardColour cardColour;
    int numberOfCards;

    /**
     * DevelopmentCardColourRequirementRepresentation constructor
     * @param cardColour colour of development cards required
     * @param numberOfCards number of development cards required
     */
    public ClientDevelopmentCardColourRequirementRepresentation(
        @JsonProperty("cardColour") DevelopmentCardColour cardColour,
        @JsonProperty("numberOfCards") int numberOfCards
    ) {
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
