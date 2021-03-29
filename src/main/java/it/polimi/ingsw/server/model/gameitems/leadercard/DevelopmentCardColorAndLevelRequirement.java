package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

public class DevelopmentCardColorAndLevelRequirement extends LeaderCardRequirement{

    public DevelopmentCardColorAndLevelRequirement(
            DevelopmentCardColour cardColour,
            DevelopmentCardLevel cardLevel,
            int numberOfCards
    ) {

    }

    @Override
    public boolean checkRequirement(PlayerContext playerContext) {
        return false;
    }
}
