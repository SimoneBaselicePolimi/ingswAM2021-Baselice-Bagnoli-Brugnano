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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FaithPathTest {

    @Mock
    GameHistory gameHistory;

    @Mock
    GameItemsManager gameItemsManager;

    Player player1, player2, player3;
    Set<Player> players;

    FaithPath faithPath;

    /**
     * Faith Path initialization.
     */
    @BeforeEach
    void setUp() {

        player1 = new Player("player1", gameItemsManager);
        player2 = new Player("player2", gameItemsManager);
        player3 = new Player("player3", gameItemsManager);

        players = Set.of(
            player1,
            player2,
            player3
        );

        faithPath = new FaithPathImp(
                20,
                List.of(
                        new VaticanReportSection(2, 3, 100),
                        new VaticanReportSection(10, 13, 200),
                        new VaticanReportSection(15, 19, 300)
                ),
                new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19},
                players,
                gameHistory
        );
    }

    /**
     * Tests the constructor of Faith Path if illegal arguments are passed as parameters:
     * size of victory points array not equals to the length of Faith Path,
     * null pointer to Players,
     * empty set of Players.
     */
    @Test
    void testWrongConstructorParams() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new FaithPathImp(4, new ArrayList<>(), new int[]{1,2}, players, gameHistory),
                "The faith path has length 4 but the size of victoryPointsByPosition is only 2 "
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new FaithPathImp(4, new ArrayList<>(), new int[]{1,2,3,4}, null, gameHistory),
                "Initializing a FaithPath with 0 players should not be allowed"
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new FaithPathImp(4, new ArrayList<>(), new int[]{1,2,3,4}, new HashSet<>(), gameHistory),
                "Initializing a FaithPath with 0 players should not be allowed"
        );

        assertDoesNotThrow(
                () -> new FaithPathImp(4, null, new int[]{1,2,3,4}, players, gameHistory)
        );
    }

    /**
     * Tests the move method, checking if the final Player position equals the position before moving + number of steps.
     * If this sum exceed the Faith Path length, the Player position equals the Faith Path length.
     */
        @Test
    void testMove() {

        FaithPath basicFaithPath = new FaithPathImp(10, new ArrayList<>(), new int[]{0,0,0,0,0,0,0,0,0,0}, players, gameHistory);
        assertEquals(0, basicFaithPath.getPlayerFaithPosition(player1));
        assertEquals(0, basicFaithPath.getPlayerFaithPosition(player2));
        assertEquals(0, basicFaithPath.getPlayerFaithPosition(player3));

        basicFaithPath.move(player1, 2);
        assertEquals(2, basicFaithPath.getPlayerFaithPosition(player1));
        assertEquals(0, basicFaithPath.getPlayerFaithPosition(player2));
        assertEquals(0, basicFaithPath.getPlayerFaithPosition(player3));

        basicFaithPath.move(player2, 8);
        assertEquals(2, basicFaithPath.getPlayerFaithPosition(player1));
        assertEquals(8, basicFaithPath.getPlayerFaithPosition(player2));
        assertEquals(0, basicFaithPath.getPlayerFaithPosition(player3));

        basicFaithPath.move(player1, 3);
        assertEquals(5, basicFaithPath.getPlayerFaithPosition(player1));
        assertEquals(8, basicFaithPath.getPlayerFaithPosition(player2));
        assertEquals(0, basicFaithPath.getPlayerFaithPosition(player3));

        basicFaithPath.move(player2, 3000);
        assertEquals(5, basicFaithPath.getPlayerFaithPosition(player1));
        assertEquals(9, basicFaithPath.getPlayerFaithPosition(player2));
        assertEquals(0, basicFaithPath.getPlayerFaithPosition(player3));
    }

    /**
     * Tests the method which states if the last position of the Faith Path is reached by a Player after moving.
     */
    @Test
    void testLastPositionReached() {
        FaithPath basicFaithPath = new FaithPathImp(10, new ArrayList<>(), new int[]{0,0,0,0,0,0,0,0,0,0}, players, gameHistory);
        assertFalse(basicFaithPath.lastPositionHasBeenReached());

        basicFaithPath.move(player1, 2);
        assertFalse(basicFaithPath.lastPositionHasBeenReached());

        basicFaithPath.move(player2, 8);
        assertFalse(basicFaithPath.lastPositionHasBeenReached());

        basicFaithPath.move(player1, 3);
        assertFalse(basicFaithPath.lastPositionHasBeenReached());

        basicFaithPath.move(player2, 3000);
        assertTrue(basicFaithPath.lastPositionHasBeenReached());
    }

    /**
     * Test the Faith Path Event returned by the move method: it checks if at least one Vatican Report happened and
     * if the final position of Faith Path is reached by a Player after moving.
     */
    @Test
    void testMoveReturnEvent() {

        FaithPathEvent e1 = faithPath.move(player1, 1);
        assertFalse(e1.hasVaticanMeetingHappened());
        assertFalse(e1.isEndReached());

        FaithPathEvent e2 = faithPath.move(player2, 2);
        assertFalse(e2.hasVaticanMeetingHappened());
        assertFalse(e2.isEndReached());

        FaithPathEvent e3 = faithPath.move(player3, 13); //Pope space reached by Player 3
        assertTrue(e3.hasVaticanMeetingHappened());
        assertFalse(e3.isEndReached());

        FaithPathEvent e4 = faithPath.move(player3, 6); //Last position reached (13+6)
        assertTrue(e4.hasVaticanMeetingHappened());
        assertTrue(e4.isEndReached());

    }

    /**
     * Tests the method to get the updated state of Players' Pope's Favor cards before and after a Vatican Report.
     */
    @Test
    void testGetPlayerPopeFavorCardsState() {

        faithPath.move(player1, 1);
        assertEquals(
                List.of(PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN),
                faithPath.getPlayerPopeFavorCardsState(player1)
        );
        assertEquals(
                List.of(PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN),
                faithPath.getPlayerPopeFavorCardsState(player2)
        );
        assertEquals(
                List.of(PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN),
                faithPath.getPlayerPopeFavorCardsState(player3)
        );

        faithPath.move(player2, 2);
        assertEquals(
                List.of(PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN),
                faithPath.getPlayerPopeFavorCardsState(player1)
        );
        assertEquals(
                List.of(PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN),
                faithPath.getPlayerPopeFavorCardsState(player2)
        );
        assertEquals(
                List.of(PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN),
                faithPath.getPlayerPopeFavorCardsState(player3)
        );

        faithPath.move(player3, 3); //First pope space reached by Player 3 (position 3)
        assertEquals(
                List.of(PopeFavorCardState.DISCARDED, PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN),
                faithPath.getPlayerPopeFavorCardsState(player1)
        );
        assertEquals(
                List.of(PopeFavorCardState.ACTIVE, PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN),
                faithPath.getPlayerPopeFavorCardsState(player2)
        );
        assertEquals(
                List.of(PopeFavorCardState.ACTIVE, PopeFavorCardState.HIDDEN, PopeFavorCardState.HIDDEN),
                faithPath.getPlayerPopeFavorCardsState(player3)
        );

        faithPath.move(player1, 18); //2nd and 3rd Vatican Reports triggered by Player 1 (1 -> 19)
        assertEquals(
                List.of(PopeFavorCardState.DISCARDED, PopeFavorCardState.ACTIVE, PopeFavorCardState.ACTIVE),
                faithPath.getPlayerPopeFavorCardsState(player1)
        );
        assertEquals(
                List.of(PopeFavorCardState.ACTIVE, PopeFavorCardState.DISCARDED, PopeFavorCardState.DISCARDED),
                faithPath.getPlayerPopeFavorCardsState(player2)
        );
        assertEquals(
                List.of(PopeFavorCardState.ACTIVE, PopeFavorCardState.DISCARDED, PopeFavorCardState.DISCARDED),
                faithPath.getPlayerPopeFavorCardsState(player3)
        );

    }

    /**
     * Tests the Victory Points count, based on the position of Players in the Faith Path and the state of their
     * Pope's Favor cards.
     */
    @Test
    void testGetPlayerVictoryPoints() {

        faithPath.move(player1, 1);
        assertEquals(1, faithPath.getPlayerVictoryPoints(player1));
        assertEquals(0, faithPath.getPlayerVictoryPoints(player2));
        assertEquals(0, faithPath.getPlayerVictoryPoints(player3));

        faithPath.move(player2, 2);
        assertEquals(1, faithPath.getPlayerVictoryPoints(player1));
        assertEquals(2, faithPath.getPlayerVictoryPoints(player2));
        assertEquals(0, faithPath.getPlayerVictoryPoints(player3));

        faithPath.move(player3, 3); //First pope space reached by Player 3 (position 3)
        assertEquals(1, faithPath.getPlayerVictoryPoints(player1));
        assertEquals(2 + 100, faithPath.getPlayerVictoryPoints(player2));
        assertEquals(3 + 100, faithPath.getPlayerVictoryPoints(player3));

        faithPath.move(player2, 8);
        assertEquals(1, faithPath.getPlayerVictoryPoints(player1));
        assertEquals(2 + 8 + 100, faithPath.getPlayerVictoryPoints(player2));
        assertEquals(3 + 100, faithPath.getPlayerVictoryPoints(player3));

        faithPath.move(player1, 18); //2nd and 3rd Vatican Reports triggered by Player 1 (1 -> 19)
        assertEquals(1 + 18 + 200 + 300, faithPath.getPlayerVictoryPoints(player1));
        assertEquals(2 + 8 + 100 + 200, faithPath.getPlayerVictoryPoints(player2));
        assertEquals(3 + 100, faithPath.getPlayerVictoryPoints(player3));

    }

}