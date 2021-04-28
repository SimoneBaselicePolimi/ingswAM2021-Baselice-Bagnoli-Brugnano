package it.polimi.ingsw.server.model.leadercard;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItemTest;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirement;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;

import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class LeaderCardTest implements IdentifiableItemTest<LeaderCard> {

    @Mock
    GameItemsManager gameItemsManager;

    @Mock
    PlayerContext player;

    @Mock
    LeaderCardRequirement trueRequirement1;

    @Mock
    LeaderCardRequirement trueRequirement2;

    @Mock
    LeaderCardRequirement falseRequirement1;

    @Mock
    LeaderCardRequirement falseRequirement2;
    
    @BeforeEach
    void setUp() {
        lenient().when(trueRequirement1.checkRequirement(eq(player))).thenReturn(true);
        lenient().when(trueRequirement2.checkRequirement(eq(player))).thenReturn(true);
        lenient().when(falseRequirement1.checkRequirement(eq(player))).thenReturn(false);
        lenient().when(falseRequirement2.checkRequirement(eq(player))).thenReturn(false);
    }

    /**
     * Tests the construction of a Leader Card
     * Checks if areRequirementsSatisfied method return true using a list of
     * requirements that must be accepted.
     * Also checks if activateLeaderCard method throws no exception using a list of
     * requirements that must be accepted.
     */
    @Test
    void testTrueRequirementsLeaderCard (){
        List <LeaderCardRequirement> listTrueRequirements = new ArrayList<>();
        listTrueRequirements.add(trueRequirement1);
        listTrueRequirements.add(trueRequirement2);

        LeaderCard leaderCard1 = new LeaderCard(
            "1", gameItemsManager, listTrueRequirements, null, null, null,null, 3
        );
        assertTrue(() -> leaderCard1.areRequirementsSatisfied(player));
        assertDoesNotThrow(() ->leaderCard1.activateLeaderCard(player));

        LeaderCard leaderCard2 = new LeaderCard(
            "2", gameItemsManager, listTrueRequirements, null, null, null,null, 2
        );
        assertTrue(() -> leaderCard2.areRequirementsSatisfied(player));
        assertDoesNotThrow(() ->leaderCard2.activateLeaderCard(player));
    }

    /**
     * Tests the construction of a Leader Card
     * Checks if areRequirementsSatisfied method return false using a list of requirements that must not be accepted
     * (it is enough that there is only one non-admitted requirement and the method must return false).
     * Also checks if activateLeaderCard method throws exception using a list of requirements that must not be accepted.
     */
    @Test
    void TestFalseRequirementsLeaderCard() {
        List <LeaderCardRequirement> listFalseRequirements = new ArrayList<>();
        List <LeaderCardRequirement> listTrueAndFalseRequirements = new ArrayList<>();
        listFalseRequirements.add(falseRequirement1);
        listFalseRequirements.add(falseRequirement2);
        listTrueAndFalseRequirements.add(trueRequirement1);
        listTrueAndFalseRequirements.add(falseRequirement2);

        LeaderCard leaderCard3 = new LeaderCard(
            "3", gameItemsManager, listFalseRequirements, null, null, null, null, 2
        );
        assertFalse(() -> leaderCard3.areRequirementsSatisfied(player));
        assertThrows(LeaderCardRequirementsNotSatisfiedException.class, () -> leaderCard3.activateLeaderCard(player));

        LeaderCard leaderCard4 = new LeaderCard(
            "4", gameItemsManager, listTrueAndFalseRequirements, null, null, null, null, 2
        );
        assertFalse(() -> leaderCard4.areRequirementsSatisfied(player));
        assertThrows(LeaderCardRequirementsNotSatisfiedException.class, () -> leaderCard3.activateLeaderCard(player));
    }

    /**
     * Test the activation of a card (from HIDDEN to ACTIVE)
     * Throw an exception if the current card state is not HIDDEN.
     */
    @Test
    void testActivateLeaderCard(){
        List <LeaderCardRequirement> listTrueRequirements = new ArrayList<>();
        listTrueRequirements.add(trueRequirement1);
        listTrueRequirements.add(trueRequirement2);

        LeaderCard leaderCard1 = new LeaderCard(
            "1", gameItemsManager, listTrueRequirements, null,null, null,null, 3
        );
        assertEquals(leaderCard1.getState(), LeaderCardState.HIDDEN);
        assertDoesNotThrow(() ->leaderCard1.activateLeaderCard(player));
        assertEquals(leaderCard1.getState(), LeaderCardState.ACTIVE);
        assertThrows(LeaderCardRequirementsNotSatisfiedException.class, () -> leaderCard1.activateLeaderCard(player));
    }

    /**
     * Test change the state of the leader card (from HIDDEN to DISCARDED).
     * Throw an exception if the current card state is not HIDDEN.
     */
    @Test
    void testDiscardLeaderCard(){
        List <LeaderCardRequirement> listTrueRequirements = new ArrayList<>();
        listTrueRequirements.add(trueRequirement1);
        listTrueRequirements.add(trueRequirement2);

        LeaderCard leaderCard1 = new LeaderCard(
            "1", gameItemsManager, listTrueRequirements,null, null, null,null, 3
        );
        assertEquals(leaderCard1.getState(), LeaderCardState.HIDDEN);
        assertDoesNotThrow(leaderCard1::discardLeaderCard);
        assertEquals(leaderCard1.getState(), LeaderCardState.DISCARDED);
        assertThrows(LeaderCardRequirementsNotSatisfiedException.class, leaderCard1::discardLeaderCard);
    }

    @Test
    void testGetLeaderCardId() {
        String id = "1";
        LeaderCard leaderCard1 = new LeaderCard(
            "1", gameItemsManager, new ArrayList<>(), null, null, null,null, 3
        );
        assertEquals(id, leaderCard1.getItemId());
    }

    @Override
    public LeaderCard initializeItemWithId(String id) {
        return new LeaderCard(
            id, gameItemsManager, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0
        );
    }

}
