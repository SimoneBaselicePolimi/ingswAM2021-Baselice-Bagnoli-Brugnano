package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;

public class ServerDevelopmentCardColourRequirementRepresentation extends ServerLeaderCardRequirementRepresentation {

    public final DevelopmentCardColour cardColour;
    public final int numberOfCards;

    /**
     * DevelopmentCardColourRequirementRepresentation constructor
     * @param cardColour colour of development cards required
     * @param numberOfCards number of development cards required
     */
    public ServerDevelopmentCardColourRequirementRepresentation(
        @JsonProperty("cardColour") DevelopmentCardColour cardColour,
        @JsonProperty("numberOfCards") int numberOfCards
    ) {
        this.cardColour = cardColour;
        this.numberOfCards = numberOfCards;
    }
}
