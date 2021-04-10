package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Set;

public class GameSetupStateTest {

    @Mock
    GameContext gameContext;

    @Mock
    LeaderCard card1;
    LeaderCard card2;
    LeaderCard card3;
    LeaderCard card4;
    LeaderCard card5;
    LeaderCard card6;
    LeaderCard card7;
    LeaderCard card8;
    LeaderCard card9;
    LeaderCard card10;
    LeaderCard card11;
    LeaderCard card12;


    Set <LeaderCard> leaderCards = Set.of(
                card1,
                card2,
                card3,
                card4,
                card5,
                card6,
                card7,
                card8,
                card9,
                card10,
                card11,
                card12
    );

    @Test
    void testRandomShuffle(){
        GameSetupState state = new GameSetupState(gameContext, leaderCards, 3);
    }
}


//@Test
//    void testRandomShuffle() {
//        ShuffledCardDeck<String> deck1 = new ShuffledCardDeck<>(new Random(1), testCards);
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