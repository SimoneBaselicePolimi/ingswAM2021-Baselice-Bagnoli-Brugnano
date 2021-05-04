package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.configfile.GameInfoConfig;
import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.network.servermessage.InitialChoicesServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.testutils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameSetupStateTest {

    Player player1 = new Player("first");
    Player player2 = new Player("second");
    Player player3 = new Player("third");

    List<Player> playersInOrder = List.of(player1, player2, player3);

    @Mock
    GameManager gameManager;

    @Mock
    GameContext gameContext;

    @Mock
    GameItemsManager gameItemsManager;

    @Mock
    GameHistory gameHistory;

    Set<LeaderCard> leaderCards;

    int maxNumberOfPlayers = 3;
    int numberOfLeadersCardsGivenToThePlayer = 4;
    int numberOfLeadersCardsThePlayerKeeps = 2;
    Map<Player, Integer> playerInitialFaithPoints = Map.of(
        player1, 0,
        player2, 1,
        player3, 3
    );
    Map<Player, Integer> playerInitialStarResources = Map.of(
        player1, 0,
        player2, 1,
        player3, 2
    );

    GameRules gameRules;

    Set<GameUpdate> mockUpdates;

    @BeforeEach
    void setUp() {
        leaderCards = TestUtils.generateSetOfMockWithID(LeaderCard.class, 15);

        Map<Integer, GameInfoConfig.GameSetup.InitialPlayerResourcesAndFaithPoints> initialResources = new HashMap<>();
        for (int i = 0; i < playersInOrder.size(); i++){
            Player player = playersInOrder.get(i);
            initialResources.put(
                i + 1,
                new GameInfoConfig.GameSetup.InitialPlayerResourcesAndFaithPoints(
                    playerInitialStarResources.get(player),
                    playerInitialFaithPoints.get(player))
                );
        }

        gameRules = new GameRules(
            new GameInfoConfig(
                maxNumberOfPlayers,
                false,
                new GameInfoConfig.GameSetup(
                    numberOfLeadersCardsGivenToThePlayer,
                    numberOfLeadersCardsThePlayerKeeps,
                    initialResources
                ),
                0,
                null,
                null,
                false,
                false
            ),
            null,
            null,
            null,
            null
        );

        mockUpdates = Set.of(
            mock(GameUpdate.class),
            mock(GameUpdate.class),
            mock(GameUpdate.class),
            mock(GameUpdate.class)
        );

        when(gameManager.getGameContext()).thenReturn(gameContext);
        when(gameContext.getPlayersTurnOrder()).thenReturn(playersInOrder);
        when(gameManager.getPlayers()).thenReturn(new HashSet<>(playersInOrder));
        when(gameManager.getGameItemsManager()).thenReturn(gameItemsManager);
        when(gameItemsManager.getAllItemsOfType(eq(LeaderCard.class))).thenReturn(leaderCards);
        when(gameManager.getGameRules()).thenReturn(gameRules);
        lenient().when(gameManager.getGameHistory()).thenReturn(gameHistory);
        lenient().when(gameManager.getAllGameUpdates()).thenReturn(mockUpdates);

    }


    @Test
    void testRandomShuffle() {

        GameSetupState state1 = new GameSetupState(new Random(1), gameManager);
        GameSetupState state1Copy = new GameSetupState(new Random(1), gameManager);
        GameSetupState state2 = new GameSetupState(new Random(2), gameManager);

        assertEquals(state1.leaderCardsGivenToThePlayers, state1Copy.leaderCardsGivenToThePlayers);
        assertNotEquals(state1.leaderCardsGivenToThePlayers, state2.leaderCardsGivenToThePlayers);
    }


    @Test
    void testInitialServerMessage() {

        GameSetupState state = new GameSetupState(gameManager);
        Map<Player, InitialChoicesServerMessage> serverMessages = state.getInitialServerMessage();
        Set<LeaderCard> alreadyAssignedCard = new HashSet<>();
        for (Player player : serverMessages.keySet()) {

            InitialChoicesServerMessage message = serverMessages.get(player);

            assertEquals(
                playerInitialStarResources.get(player),
                message.numberOfStarResources
            );

            assertEquals(numberOfLeadersCardsGivenToThePlayer, message.leaderCardsGivenToThePlayer.size());
            message.leaderCardsGivenToThePlayer.forEach(c -> assertFalse(alreadyAssignedCard.contains(c)));
            alreadyAssignedCard.addAll(message.leaderCardsGivenToThePlayer);

            assertEquals(mockUpdates, message.gameUpdates);
        }
    }

    @Test
    void testHandleInitialChoiceCR(){

        GameSetupState state = new GameSetupState(gameManager);
        state.getInitialServerMessage();
        Iterator<LeaderCard> iter = leaderCards.iterator();

        LeaderCard card1 = iter.next();
        LeaderCard card2 = iter.next();
        Set<LeaderCard> leaderCardsChosenByThePlayer = Set.of(card1, card2);

        Map<ResourceStorage, Map<ResourceType, Integer>> chosenResourcesToAddByStorage;

        Map<ResourceType, Integer> resources = new HashMap<>();
        //for (Player player : serverMessages.keySet()) {

           // InitialChoicesServerMessage message = serverMessages.get(player);

           // assertEquals(mockUpdates, message.gameUpdates);
        //}
    }

}
