package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;

public class DevelopmentCardColorRequirement extends LeaderCardRequirement{
    DevelopmentCardColour cardColour;
    int numberOfCards;

    public DevelopmentCardColorRequirement(DevelopmentCardColour cardColour, int numberOfCards) {
        this.cardColour = cardColour;
        this.numberOfCards = numberOfCards;
    }

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
