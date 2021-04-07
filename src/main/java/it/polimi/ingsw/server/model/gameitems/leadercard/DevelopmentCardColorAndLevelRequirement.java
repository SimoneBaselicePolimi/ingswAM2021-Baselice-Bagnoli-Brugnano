package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

/**
 * This class represent the request for a specific number of development cards of a certain color and level
 */
public class DevelopmentCardColorAndLevelRequirement extends LeaderCardRequirement {
    DevelopmentCardColour cardColour;
    DevelopmentCardLevel cardLevel;
    int numberOfCards;

    /**
     * DevelopmentCardColorAndLevelRequirement constructor
     * @param cardColour colour of development cards required
     * @param cardLevel level of development cards required
     * @param numberOfCards number of development cards required
     */
    public DevelopmentCardColorAndLevelRequirement(
            DevelopmentCardColour cardColour,
            DevelopmentCardLevel cardLevel,
            int numberOfCards
    ) {
        this.cardColour=cardColour;
        this.cardLevel=cardLevel;
        this.numberOfCards=numberOfCards;
    }

    /**
     * Method to verify that the player has the necessary development cards (with a specific colour and level)
     * to activate a leader card
     * @param playerContext reference to the single player
     * @return true if the player has a specific number of development cards of a certain color and level
     */
    @Override
    public boolean checkRequirement(PlayerContext playerContext) {
        int sumOfRightColourAndLevelCard = 0;
        for (DevelopmentCard developmentCard : playerContext.getAllDevelopmentCards()){
            if (developmentCard.getColour() == cardColour && developmentCard.getLevel() == cardLevel)
                sumOfRightColourAndLevelCard ++;
        }
        if (sumOfRightColourAndLevelCard >= numberOfCards)
            return true;
        return false;
    }
}
