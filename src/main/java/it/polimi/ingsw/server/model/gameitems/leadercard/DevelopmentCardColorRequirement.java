package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;

/**
 * request for a specific number of development cards of a certain color
 */
public class DevelopmentCardColorRequirement extends LeaderCardRequirement{
    DevelopmentCardColour cardColour;
    int numberOfCards;

    /**
     * DevelopmentCardColorRequirement constructor
     * @param cardColour
     * @param numberOfCards
     */
    public DevelopmentCardColorRequirement(DevelopmentCardColour cardColour, int numberOfCards) {
        this.cardColour = cardColour;
        this.numberOfCards = numberOfCards;
    }
    /**
     * @param playerContext
     * @return true if the player has a specific number of development cards of a certain color
     */
    @Override
    public boolean checkRequirement(PlayerContext playerContext) {
        int sumOfRightColourCard = 0;
        for (DevelopmentCard developmentCard : playerContext.getAllDevelopmentCards()){
            if (developmentCard.getColour() == cardColour)
                sumOfRightColourCard ++;
        }
        if (sumOfRightColourCard >= numberOfCards)
            return true;
        return false;
    }
}
