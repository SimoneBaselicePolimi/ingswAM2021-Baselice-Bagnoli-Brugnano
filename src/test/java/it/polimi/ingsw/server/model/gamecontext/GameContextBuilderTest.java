package it.polimi.ingsw.server.model.gamecontext;

import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameContextBuilderTest {

    @Mock
    GameRules gameRules;

    @Mock
    GameItemsManager gameItemsManager;

    Player player1, player2, player3, player4;

    Set<Player> players;

    @BeforeEach
    void setUp() {
        player1 = new Player("id1");
        player2 = new Player("id2");
        player3 = new Player("id3");
        player4 = new Player("id4");

        players = Set.of(
            player1,
            player2,
            player3,
            player4
        );
    }

    @Test
    void generateRandomPlayersOrderTest() {
        List<Player> randomList1 =
            new GameContextBuilder(players, gameRules, gameItemsManager, new Random(1)).generateRandomPlayersOrder();
        List<Player> randomList1Copy =
            new GameContextBuilder(players, gameRules, gameItemsManager, new Random(1)).generateRandomPlayersOrder();
        List<Player> randomList2 =
            new GameContextBuilder(players, gameRules, gameItemsManager, new Random(2)).generateRandomPlayersOrder();
        assertEquals(randomList1, randomList1Copy);
        assertNotEquals(randomList1, randomList2);
    }



}