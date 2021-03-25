package it.polimi.ingsw.server.model.gamecontext.faith;

import it.polimi.ingsw.server.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FaithPathTest {

    Player player1 = new Player("player1");
    Player player2 = new Player("player2");
    Player player3 = new Player("player3");
    Set<Player> players = Set.of(
            player1,
            player2,
            player3
    );
    FaithPath faithPath;

    @BeforeEach
    void setUp() {
        faithPath = new FaithPath(
                20,
                List.of(
                        new VaticanReportSection(2, 3, 100),
                        new VaticanReportSection(10, 13, 200),
                        new VaticanReportSection(15, 18, 300)
                ),
                new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19},
                players
        );
    }

    void testWrongConstructorParams() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new FaithPath(4, new ArrayList<>(), new int[]{1,2}, players),
                "The faith path has length 4 but the size of victoryPointsByPosition is only 2 "
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new FaithPath(4, new ArrayList<>(), new int[]{1,2,3,4}, null),
                "Initializing a FaithPath with 0 players should not be allowed"
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new FaithPath(4, new ArrayList<>(), new int[]{1,2,3,4}, new HashSet<>()),
                "Initializing a FaithPath with 0 players should not be allowed"
        );
        assertDoesNotThrow(
                () -> new FaithPath(4, null, new int[]{1,2,3,4}, players)
        );
    }

        @Test
    void testMove() {

        FaithPath basicFaithPath = new FaithPath(10, new ArrayList<>(), new int[]{0,0,0,0,0,0,0,0,0,0}, players);
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

    @Test
    void testLastPositionReached() {
        FaithPath basicFaithPath = new FaithPath(10, new ArrayList<>(), new int[]{0,0,0,0,0,0,0,0,0,0}, players);
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

    @Test
    void testMoveReturnEvent() {

        FaithPathEvent e1 = faithPath.move(player1, 1);
        assertFalse(e1.hasVaticanMeetingHappened());
        assertFalse(e1.isEndReached());

        FaithPathEvent e2 = faithPath.move(player2, 2);
        assertFalse(e2.hasVaticanMeetingHappened());
        assertFalse(e2.isEndReached());

        FaithPathEvent e3 = faithPath.move(player3, 3); //First pope space reached by Player 3 (position 3)
        assertTrue(e3.hasVaticanMeetingHappened());
        assertFalse(e3.isEndReached());

        FaithPathEvent e4 = faithPath.move(player1, 19); //Last position reached (1 + 19)
        assertTrue(e4.hasVaticanMeetingHappened());
        assertTrue(e4.isEndReached());

    }

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

        faithPath.move(player1, 17); //2nd and 3rd vatican reports triggered by Player 1 (1 -> 18)
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
        assertEquals(3 + 300, faithPath.getPlayerVictoryPoints(player3));

        faithPath.move(player2, 8);
        assertEquals(1, faithPath.getPlayerVictoryPoints(player1));
        assertEquals(2 + 8 + 100, faithPath.getPlayerVictoryPoints(player2));
        assertEquals(3 + 100, faithPath.getPlayerVictoryPoints(player3));

        faithPath.move(player1, 17); //2nd and 3rd vatican reports triggered by Player 1 (1 -> 18)
        assertEquals(1 + 17 + 200 + 300, faithPath.getPlayerVictoryPoints(player1));
        assertEquals(2 + 8 + 100 + 200, faithPath.getPlayerVictoryPoints(player2));
        assertEquals(3 + 100, faithPath.getPlayerVictoryPoints(player3));

    }

}