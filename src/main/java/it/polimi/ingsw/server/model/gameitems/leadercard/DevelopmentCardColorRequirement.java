package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;

public class DevelopmentCardColorRequirement extends LeaderCardRequirement{

    public DevelopmentCardColorRequirement(DevelopmentCardColour cardColour, int numberOfCards) {

    }

    @Override
    public boolean checkRequirement(PlayerContext playerContext) {
        return false;
    }
}
