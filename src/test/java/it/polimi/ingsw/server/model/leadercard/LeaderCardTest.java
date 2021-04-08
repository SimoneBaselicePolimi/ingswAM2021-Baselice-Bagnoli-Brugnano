package it.polimi.ingsw.server.model.leadercard;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirement;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;

import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class LeaderCardTest {

    /**
     * Tests the construction of a Leader Card
     * Checks if areRequirementsSatisfied method return true using a list of
     * requirements that must be accepted.
     * Also checks if activateLeaderCard method throws no exception using a list of
     * requirements that must be accepted.
     */
    @Test
    void testTrueRequirementsLeaderCard (){
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

    /**
     * Tests the construction of a Leader Card
     * Checks if areRequirementsSatisfied method return false using a list of requirements that must not be accepted
     * (it is enough that there is only one non-admitted requirement and the method must return false).
     * Also checks if activateLeaderCard method throws exception using a list of requirements that must not be accepted.
     */
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

    /**
     * Test the activation of a card (from HIDDEN to ACTIVE)
     * Throw an exception if the current card state is not HIDDEN.
     */
    @Test
    void testActivateLeaderCard(){
        PlayerContext player1 = new PlayerContext();
        List <LeaderCardRequirement> listTrueRequirements = new ArrayList<LeaderCardRequirement>();
        listTrueRequirements.add(new TrueRequirement1());
        listTrueRequirements.add(new TrueRequirement2());

        LeaderCard leaderCard1 = new LeaderCard(listTrueRequirements, null,
                null, null,null, 3);
        assertEquals(leaderCard1.getState(), LeaderCardState.HIDDEN);
        assertDoesNotThrow(() ->leaderCard1.activateLeaderCard(player1));
        assertEquals(leaderCard1.getState(), LeaderCardState.ACTIVE);
        assertThrows(LeaderCardRequirementsNotSatisfiedException.class, () -> leaderCard1.activateLeaderCard(player1));
    }

    /**
     * Test change the state of the leader card (from HIDDEN to DISCARDED).
     * Throw an exception if the current card state is not HIDDEN.
     */
    @Test
    void testDiscardLeaderCard(){
        PlayerContext player1 = new PlayerContext();
        List <LeaderCardRequirement> listTrueRequirements = new ArrayList<LeaderCardRequirement>();
        listTrueRequirements.add(new TrueRequirement1());
        listTrueRequirements.add(new TrueRequirement2());

        LeaderCard leaderCard1 = new LeaderCard(listTrueRequirements, null,
                null, null,null, 3);
        assertEquals(leaderCard1.getState(), LeaderCardState.HIDDEN);
        assertDoesNotThrow(() ->leaderCard1.discardLeaderCard());
        assertEquals(leaderCard1.getState(), LeaderCardState.DISCARDED);
        assertThrows(LeaderCardRequirementsNotSatisfiedException.class, () -> leaderCard1.discardLeaderCard());
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
