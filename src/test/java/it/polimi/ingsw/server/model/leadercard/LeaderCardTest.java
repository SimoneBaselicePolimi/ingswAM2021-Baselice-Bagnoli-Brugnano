package it.polimi.ingsw.server.model.leadercard;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItemTest;
import it.polimi.ingsw.server.model.gameitems.leadercard.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Set <LeaderCardRequirement> setTrueRequirements = new HashSet<>();
        setTrueRequirements.add(trueRequirement1);
        setTrueRequirements.add(trueRequirement2);

        LeaderCard leaderCard1 = new LeaderCardImp(
            "1", gameItemsManager, setTrueRequirements, null, null, null,null, 3
        );
        assertTrue(() -> leaderCard1.areRequirementsSatisfied(player));
        assertDoesNotThrow(() ->leaderCard1.activateLeaderCard(player));

        LeaderCard leaderCard2 = new LeaderCardImp(
            "2", gameItemsManager, setTrueRequirements, null, null, null,null, 2
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
        Set <LeaderCardRequirement> setFalseRequirements = new HashSet<>();
        Set <LeaderCardRequirement> listTrueAndFalseRequirements = new HashSet<>();
        setFalseRequirements.add(falseRequirement1);
        setFalseRequirements.add(falseRequirement2);
        listTrueAndFalseRequirements.add(trueRequirement1);
        listTrueAndFalseRequirements.add(falseRequirement2);

        LeaderCard leaderCard3 = new LeaderCardImp(
            "3", gameItemsManager, setFalseRequirements, null, null, null, null, 2
        );
        assertFalse(() -> leaderCard3.areRequirementsSatisfied(player));
        assertThrows(LeaderCardRequirementsNotSatisfiedException.class, () -> leaderCard3.activateLeaderCard(player));

        LeaderCard leaderCard4 = new LeaderCardImp(
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
        Set <LeaderCardRequirement> setTrueRequirements = new HashSet<>();
        setTrueRequirements.add(trueRequirement1);
        setTrueRequirements.add(trueRequirement2);

        LeaderCard leaderCard1 = new LeaderCardImp(
            "1", gameItemsManager, setTrueRequirements, null,null, null,null, 3
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
        Set <LeaderCardRequirement> setTrueRequirements = new HashSet<>();
        setTrueRequirements.add(trueRequirement1);
        setTrueRequirements.add(trueRequirement2);

        LeaderCard leaderCard1 = new LeaderCardImp(
            "1", gameItemsManager, setTrueRequirements,null, null, null,null, 3
        );
        assertEquals(leaderCard1.getState(), LeaderCardState.HIDDEN);
        assertDoesNotThrow(leaderCard1::discardLeaderCard);
        assertEquals(leaderCard1.getState(), LeaderCardState.DISCARDED);
        assertThrows(LeaderCardRequirementsNotSatisfiedException.class, leaderCard1::discardLeaderCard);
    }

    @Test
    void testGetLeaderCardId() {
        String id = "1";
        LeaderCard leaderCard1 = new LeaderCardImp(
            "1", gameItemsManager, new HashSet<>(), null, null, null,null, 3
        );
        assertEquals(id, leaderCard1.getItemId());
    }

    @Override
    public LeaderCard initializeItemWithId(String id) {
        return new LeaderCardImp(
            id, gameItemsManager, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), 0
        );
    }

}
