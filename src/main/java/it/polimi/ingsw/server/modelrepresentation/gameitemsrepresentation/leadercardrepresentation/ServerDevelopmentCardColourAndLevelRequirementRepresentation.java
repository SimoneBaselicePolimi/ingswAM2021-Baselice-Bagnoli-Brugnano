package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

public class ServerDevelopmentCardColourAndLevelRequirementRepresentation extends ServerLeaderCardRequirementRepresentation {

    public final DevelopmentCardColour cardColour;
    public final DevelopmentCardLevel cardLevel;
    public final int numberOfCards;

    /**
     * DevelopmentCardColourAndLevelRequirementRepresentation constructor
     * @param cardColour colour of development cards required
     * @param cardLevel level of development cards required
     * @param numberOfCards number of development cards required
     */
    public ServerDevelopmentCardColourAndLevelRequirementRepresentation(
        @JsonProperty("cardColour") DevelopmentCardColour cardColour,
        @JsonProperty("cardLevel") DevelopmentCardLevel cardLevel,
        @JsonProperty("numberOfCards") int numberOfCards
    ) {
        this.cardColour=cardColour;
        this.cardLevel=cardLevel;
        this.numberOfCards=numberOfCards;
    }
}
