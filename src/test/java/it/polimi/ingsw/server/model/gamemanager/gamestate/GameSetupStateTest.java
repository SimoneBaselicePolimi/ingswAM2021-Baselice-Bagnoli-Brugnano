package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.lenient;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;

public class GameSetupStateTest {

    Player player1 = new Player("first");
    Player player2 = new Player("second");
    Player player3 = new Player("third");
    Player player4 = new Player("fourth");

    List<Player> players = List.of(player1, player2, player3, player4);

    @Mock
    GameManager gameManager;

    @Mock
    GameContext gameContext;

    @BeforeEach
    void setUp() {
        lenient().when(this.gameManager).thenReturn(gameManager);
        lenient().when(gameManager.getGameContext()).thenReturn(gameContext);
        lenient().when(gameManager.getGameContext().getPlayersTurnOrder()).thenReturn(players);
    }

    LeaderCard card1 = new LeaderCard(
        "L1",
        null,
        null,
        null,
        null,
        null,
        3
    );

    LeaderCard card2 = new LeaderCard(
        "L2",
        null,
        null,
        null,
        null,
        null,
        2
    );

    LeaderCard card3 = new LeaderCard(
        "L3",
        null,
        null,
        null,
        null,
        null,
        7
    );

    LeaderCard card4 = new LeaderCard(
        "L4",
        null,
        null,
        null,
        null,
        null,
        6
    );

    LeaderCard card5 = new LeaderCard(
        "L5",
        null,
        null,
        null,
        null,
        null,
        1
    );

    Set<LeaderCard> leaderCards = Set.of(
        card1,
        card2,
        card3,
        card4,
        card5
    );

    @Test
    void testRandomShuffle() {
        GameSetupState state1 = new GameSetupState(new Random(1), gameManager);
        GameSetupState state1Copy = new GameSetupState(new Random(1), gameManager);
        GameSetupState state2 = new GameSetupState(new Random(2), gameManager);
        GameSetupState state3 = new GameSetupState(new Random(2), gameManager);

        assertEquals(state1.leaderCardsGivenToThePlayers, state1Copy.leaderCardsGivenToThePlayers);
    }
}

   // @Test
    //void testRandomShuffle() {
    //ShuffledCardDeck<String> deck1 = new ShuffledCardDeck<>(new Random(1), testCards);
//        List<String> listDeck1 = new ArrayList<>();
//        while(!deck1.isEmpty())
//            listDeck1.add(deck1.pop());
//
//        ShuffledCardDeck<String> deck1_copy = new ShuffledCardDeck<>(new Random(1), testCards);
//        List<String> listDeck1_copy = new ArrayList<>();
//        while(!deck1_copy.isEmpty())
//            listDeck1_copy.add(deck1_copy.pop());
//
//        ShuffledCardDeck<String> deck2 = new ShuffledCardDeck<>(new Random(2), testCards);
//        List<String> listDeck2 = new ArrayList<>();
//        while(!deck2.isEmpty())
//            listDeck2.add(deck2.pop());
//
//        assertEquals(listDeck1, listDeck1_copy);
//        assertNotEquals(listDeck1, listDeck2);
//    }