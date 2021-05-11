package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;

/**
 * This class represent the request for a specific number of development cards of a certain colour
 */
public class DevelopmentCardColourRequirement extends LeaderCardRequirement {
    public final DevelopmentCardColour cardColour;
    public final int numberOfCards;

    /**
     * DevelopmentCardColorRequirement constructor
     * @param cardColour colour of development cards required
     * @param numberOfCards number of development cards required
     */
    public DevelopmentCardColourRequirement(DevelopmentCardColour cardColour, int numberOfCards) {
        this.cardColour = cardColour;
        this.numberOfCards = numberOfCards;
    }

    /**
     * Method to verify that the player has the necessary cards (with a specific colour) to activate a leader card
     * @param playerContext reference to the single player
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
