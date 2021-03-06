package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gui.fxcontrollers.components.ColourAndLevelRequirement;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

public class ClientDevelopmentCardColourAndLevelRequirementRepresentation extends ClientLeaderCardRequirementRepresentation {

    DevelopmentCardColour cardColour;
    DevelopmentCardLevel cardLevel;
    int numberOfCards;

    /**
     * DevelopmentCardColourAndLevelRequirementRepresentation constructor
     * @param cardColour colour of development cards required
     * @param cardLevel level of development cards required
     * @param numberOfCards number of development cards required
     */
    public ClientDevelopmentCardColourAndLevelRequirementRepresentation(
        @JsonProperty("cardColour") DevelopmentCardColour cardColour,
        @JsonProperty("cardLevel") DevelopmentCardLevel cardLevel,
        @JsonProperty("numberOfCards") int numberOfCards
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

    @Override
    public String getDescription() {
        return Localization.getLocalizationInstance().getString(
            "leaderCards.requirements.colourAndLevel",
            numberOfCards,
            numberOfCards == 1 ? Localization.getLocalizationInstance().getString("cards.singular")
                : Localization.getLocalizationInstance().getString("cards.plural"),
            numberOfCards == 1 ? cardColour.getColourNameLocalizedSingular() : cardColour.getColourNameLocalizedPlural(),
            cardLevel.toValue()
        );
    }

    @Override
    public ColourAndLevelRequirement buildGuiComponent() {
        return new ColourAndLevelRequirement(this);
    }
}
