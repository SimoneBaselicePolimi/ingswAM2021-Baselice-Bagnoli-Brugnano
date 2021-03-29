package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

/**
 * /**
 *  * request for a specific number of development cards of a certain color and level
 *
 */
public class DevelopmentCardColorAndLevelRequirement extends LeaderCardRequirement{
    DevelopmentCardColour cardColour;
    DevelopmentCardLevel cardLevel;
    int numberOfCards;

    /**
     * DevelopmentCardColorAndLevelRequirement constructor
     * @param CardColour
     * @param cardLevel
     * @param numberOfCards
     */
    public DevelopmentCardColorAndLevelRequirement(
            DevelopmentCardColour CardColour,
            DevelopmentCardLevel cardLevel,
            int numberOfCards
    ) {
        this.cardColour=cardColour;
        this.cardLevel=cardLevel;
        this.numberOfCards=numberOfCards;
    }
    /**
     * @param playerContext
     * @return true if the player has a specific number of development cards of a certain color and level
     */
    @Override
    public boolean checkRequirement(PlayerContext playerContext) {
        int sumOfRightColourCard = 0;
        for (DevelopmentCard developmentCard : playerContext.getAllDevelopmentCards()){
            if (developmentCard.getColour() == cardColour && developmentCard.getLevel() == cardLevel)
                sumOfRightColourCard ++;
        }
        if (sumOfRightColourCard >= numberOfCards)
            return true;
        return false;
    }
}
