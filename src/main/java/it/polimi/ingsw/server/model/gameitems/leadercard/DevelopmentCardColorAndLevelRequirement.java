package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

public class DevelopmentCardColorAndLevelRequirement extends LeaderCardRequirement{
    DevelopmentCardColour cardColour;
    DevelopmentCardLevel cardLevel;
    int numberOfCards;

    public DevelopmentCardColorAndLevelRequirement(
            DevelopmentCardColour CardColour,
            DevelopmentCardLevel cardLevel,
            int numberOfCards
    ) {
        this.cardColour=cardColour;
        this.cardLevel=cardLevel;
        this.numberOfCards=numberOfCards;
    }

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
