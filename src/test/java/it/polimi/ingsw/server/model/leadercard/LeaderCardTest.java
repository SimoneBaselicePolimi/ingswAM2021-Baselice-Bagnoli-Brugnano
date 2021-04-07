package it.polimi.ingsw.server.model.leadercard;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirement;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class LeaderCardTest {


    @Test
    void TestTrueRequirementsLeaderCard(){
        PlayerContext player1 = new PlayerContext();
        List <LeaderCardRequirement> listTrueRequirements = new ArrayList<LeaderCardRequirement>();
        listTrueRequirements.add(new TrueRequirement1());
        listTrueRequirements.add(new TrueRequirement2());

        LeaderCard leaderCard1 = new LeaderCard(listTrueRequirements, null,
                null, null,null, 3);
        assertTrue(() -> leaderCard1.areRequirementsSatisfied(player1));
        assertDoesNotThrow(() ->leaderCard1.activateLeaderCard(player1));

        LeaderCard leaderCard2 = new LeaderCard(listTrueRequirements, null,
                null, null,null, 2);
        assertTrue(() -> leaderCard2.areRequirementsSatisfied(player1));
        assertDoesNotThrow(() ->leaderCard2.activateLeaderCard(player1));
    }

    @Test
    void TestFalseRequirementsLeaderCard() {
        PlayerContext player1 = new PlayerContext();
        List <LeaderCardRequirement> listFalseRequirements = new ArrayList<LeaderCardRequirement>();
        List <LeaderCardRequirement> listTrueAndFalseRequirements = new ArrayList<LeaderCardRequirement>();
        listFalseRequirements.add(new FalseRequirement1());
        listFalseRequirements.add(new FalseRequirement2());
        listTrueAndFalseRequirements.add(new TrueRequirement1());
        listTrueAndFalseRequirements.add(new FalseRequirement2());

        LeaderCard leaderCard3 = new LeaderCard(listFalseRequirements, null,
                null, null, null, 2);
        assertFalse(() -> leaderCard3.areRequirementsSatisfied(player1));
        assertThrows(LeaderCardRequirementsNotSatisfiedException.class, () -> leaderCard3.activateLeaderCard(player1));

        LeaderCard leaderCard4 = new LeaderCard(listTrueAndFalseRequirements, null,
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

    class FalseRequirement1 extends LeaderCardRequirement {
        @Override
        public boolean checkRequirement(PlayerContext playerContext) {
            return false;
        }
    }

    class FalseRequirement2 extends LeaderCardRequirement {
        @Override
        public boolean checkRequirement(PlayerContext playerContext) {
            return false;
        }
    }
}
