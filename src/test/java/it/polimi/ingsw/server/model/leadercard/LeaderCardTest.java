package it.polimi.ingsw.server.model.leadercard;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirement;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderCardTest {


    @Test
    void TestTrueRequirementsLeaderCard(){
        PlayerContext player1 = new PlayerContext();
        LeaderCard leaderCard1 = new LeaderCard(new TrueRequirement1(), null,
                null, null,null, 3);
        assertTrue(() -> leaderCard1.areRequirementsSatisfied(player1));
        assertDoesNotThrow(() ->leaderCard1.activateLeaderCard(player1));
        LeaderCard leaderCard2 = new LeaderCard(new TrueRequirement2(), null,
                null, null,null, 2);
        assertTrue(() -> leaderCard2.areRequirementsSatisfied(player1));
        assertDoesNotThrow(() ->leaderCard2.activateLeaderCard(player1));

    }
    @Test
    void TestFalseRequirementsLeaderCard() {
        PlayerContext player1 = new PlayerContext();
        LeaderCard leaderCard3 = new LeaderCard(new FalseRequirement(), null,
                null, null, null, 2);
        assertFalse(() -> leaderCard3.areRequirementsSatisfied(player1));
        assertThrows(LeaderCardRequirementsNotSatisfiedException.class, () -> leaderCard3.activateLeaderCard(player1));
    }




    class TrueRequirement1 extends LeaderCardRequirement {
        @Override
        public boolean checkRequirement(PlayerContext playerContext) {
            return true;
        }
    }

    class TrueRequirement2 extends LeaderCardRequirement {
        @Override
        public boolean checkRequirement(PlayerContext playerContext) {
            return true;
        }
    }

    class FalseRequirement extends LeaderCardRequirement {
        @Override
        public boolean checkRequirement(PlayerContext playerContext) {
            return false;
        }
    }
}
