package it.polimi.ingsw.server.model.gamecontext.faith;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FaithPathSinglePlayerTest {

    @Mock
    GameHistory gameHistory;

    @Mock
    GameItemsManager gameItemsManager;

    // Player Initialization
    Player singlePlayer;

    FaithPathSinglePlayer faithPath;

    /**
     * Faith Path initialization.
     */
    @BeforeEach
    void setUp() {

        singlePlayer = new Player("player1", gameItemsManager);

        faithPath = new FaithPathSinglePlayer(
                20,
                List.of(
                        new VaticanReportSection(2, 3, 100),
                        new VaticanReportSection(10, 13, 200),
                        new VaticanReportSection(15, 19, 300)
                ),
                new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19},
                singlePlayer,
                gameHistory
        );
    }

    /**
     * Tests the move method for both the single Player and the Black Cross token.
     * Checks if the final position equals the position before moving + number of steps.
     * If this sum exceed the Faith Path length, the final position equals the Faith Path length.
     */
    @Test
    void testMove() {

        FaithPathSinglePlayer basicFaithPath = new FaithPathSinglePlayer(
                10, new ArrayList<>(), new int[]{0,0,0,0,0,0,0,0,0,0}, singlePlayer, gameHistory);
        assertEquals(0, basicFaithPath.getPlayerFaithPosition(singlePlayer));
        assertEquals(0, basicFaithPath.getBlackCrossFaithPosition());

        basicFaithPath.move(singlePlayer, 2);
        assertEquals(2, basicFaithPath.getPlayerFaithPosition(singlePlayer));
        assertEquals(0, basicFaithPath.getBlackCrossFaithPosition());

        basicFaithPath.moveBlackCross(8);
        assertEquals(2, basicFaithPath.getPlayerFaithPosition(singlePlayer));
        assertEquals(8, basicFaithPath.getBlackCrossFaithPosition());

        basicFaithPath.move(singlePlayer, 3);
        assertEquals(5, basicFaithPath.getPlayerFaithPosition(singlePlayer));
        assertEquals(8, basicFaithPath.getBlackCrossFaithPosition());

        basicFaithPath.moveBlackCross(3000);
        assertEquals(5, basicFaithPath.getPlayerFaithPosition(singlePlayer));
        assertEquals(9, basicFaithPath.getBlackCrossFaithPosition());
    }

    /**
     * Test the Faith Path Event returned by the move method: it checks if at least one Vatican Report happened and
     * if the final position of Faith Path is reached by a Player (or Black Cross token) after moving.
     */
    @Test
    void testMoveReturnEvent() {

        FaithPathEvent e1 = faithPath.move(singlePlayer, 1);
        assertFalse(e1.hasVaticanMeetingHappened());
        assertFalse(e1.isEndReached());

        FaithPathEvent e2 = faithPath.moveBlackCross(2);
        assertFalse(e2.hasVaticanMeetingHappened());
        assertFalse(e2.isEndReached());

        FaithPathEvent e3 = faithPath.move(singlePlayer, 12); //Pope space reached by the Player
        assertTrue(e3.hasVaticanMeetingHappened());
        assertFalse(e3.isEndReached());

        FaithPathEvent e4 = faithPath.moveBlackCross(17); //Last position reached by the Black Cross token (2+17)
        assertTrue(e4.hasVaticanMeetingHappened());
        assertTrue(e4.isEndReached());
    }

    /**
     * Tests the method to get the updated state of Player's Pope's Favor cards before and after a Vatican Report.
     */
    @Test
    void testGetPlayerPopeFavorCardsState() {

        faithPath.move(singlePlayer, 1);
        assertEquals(
                List.of(PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN),
                faithPath.getPlayerPopeFavorCardsState(singlePlayer)
        );

        faithPath.moveBlackCross(3); //First pope space reached by Player 3 (position 3)
        assertEquals(
                List.of(PopeFavorCardState.DISCARDED, PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN),
                faithPath.getPlayerPopeFavorCardsState(singlePlayer)
        );

        faithPath.move(singlePlayer, 18); //2nd and 3rd Vatican Reports triggered by the Player (1 -> 19)
        assertEquals(
                List.of(PopeFavorCardState.DISCARDED, PopeFavorCardState.ACTIVE, PopeFavorCardState.ACTIVE),
                faithPath.getPlayerPopeFavorCardsState(singlePlayer)
        );
    }

    /**
     * Tests the Victory Points count, based on the position of the Player in the Faith Path and the state of his
     * Pope's Favor cards.
     */
    @Test
    void testGetPlayerVictoryPoints() {

        faithPath.move(singlePlayer, 1);
        assertEquals(1, faithPath.getPlayerVictoryPoints(singlePlayer));

        faithPath.moveBlackCross( 2);
        assertEquals(1, faithPath.getPlayerVictoryPoints(singlePlayer));

        faithPath.move(singlePlayer, 2); //First Vatican Report triggered by the Player (position 3)
        assertEquals(1 + 2 + 100, faithPath.getPlayerVictoryPoints(singlePlayer));

        faithPath.moveBlackCross( 11); // 2nd Vatican Report triggered by the Black Cross token
        assertEquals(1 + 2 + 100, faithPath.getPlayerVictoryPoints(singlePlayer));

        faithPath.move(singlePlayer, 16); //3rd Vatican Report triggered by the Player (3 -> 19)
        assertEquals(1 + 2 + 16 + 100 + 300, faithPath.getPlayerVictoryPoints(singlePlayer));
    }
}
