package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gui.fxcontrollers.components.ColourRequirement;
import it.polimi.ingsw.localization.Localization;
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

    @Override
    public String getDescription() {
        return Localization.getLocalizationInstance().getString(
            "leaderCards.requirements.colour",
            numberOfCards,
            numberOfCards == 1 ? Localization.getLocalizationInstance().getString("cards.singular")
                : Localization.getLocalizationInstance().getString("cards.plural"),
            numberOfCards == 1 ? cardColour.getColourNameLocalizedSingular() : cardColour.getColourNameLocalizedPlural()
        );
    }

    @Override
    public ColourRequirement buildGuiComponent() {
        return new ColourRequirement(this);
    }
}
