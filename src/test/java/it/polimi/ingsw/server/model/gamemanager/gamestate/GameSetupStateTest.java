package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.configfile.GameInfoConfig;
import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

    @Mock
    LeaderCard leaderCard1;

    @Mock
    LeaderCard leaderCard2;

    @Mock
    LeaderCard leaderCard3;

    @Mock
    LeaderCard leaderCard4;

    @Mock
    LeaderCard leaderCard5;

    @Mock
    LeaderCard leaderCard6;

    GameRules gameRules;

    @Mock
    GameInfoConfig gameInfo;

    @Mock
    GameInfoConfig.GameSetup setup;

    Set<LeaderCard> leaderCards = Set.of(
        leaderCard1,
        leaderCard2,
        leaderCard3,
        leaderCard4,
        leaderCard5,
        leaderCard6
    );

    @BeforeEach
    void setUp() {
        lenient().when(gameManager.getGameContext()).thenReturn(gameContext);
        lenient().when(gameManager.getGameContext().getPlayersTurnOrder()).thenReturn(players);


        when(leaderCard1.getItemId()).thenReturn("L1");
        when(leaderCard1.getItemId()).thenReturn("L2");
        when(leaderCard1.getItemId()).thenReturn("L3");
        when(leaderCard1.getItemId()).thenReturn("L4");
        when(leaderCard1.getItemId()).thenReturn("L5");
        when(leaderCard1.getItemId()).thenReturn("L6");

        //TODO
        /*
        gameRules = new GameRules(
            new GameInfoConfig(),
            null,
            null
        );
        */

//        GameInfoConfig gameInfo = gameManager.getGameRules().gameInfoConfig;
//        numberOfLeadersCardsGivenToThePlayer = gameInfo.gameSetup.numberOfLeadersCardsGivenToThePlayer;
//        numberOfLeadersCardsThePlayerKeeps = gameInfo.gameSetup.numberOfLeadersCardsThePlayerKeeps;

    }

    @Test
    void testRandomShuffle() {
        GameSetupState state1 = new GameSetupState(new Random(1), gameManager);
        GameSetupState state1Copy = new GameSetupState(new Random(1), gameManager);
        GameSetupState state2 = new GameSetupState(new Random(2), gameManager);

        assertEquals(state1.leaderCardsGivenToThePlayers, state1Copy.leaderCardsGivenToThePlayers);
        assertNotEquals(state1.leaderCardsGivenToThePlayers, state2.leaderCardsGivenToThePlayers);
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