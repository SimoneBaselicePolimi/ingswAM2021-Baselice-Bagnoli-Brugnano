package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.network.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gamehistory.GameAction;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.gamehistory.SetupStartedAction;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.testutils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameStateTest {

    @Mock
    GameManager gameManager;

    @Mock
    GameItemsManager gameItemsManager;

    @Mock
    GameHistory gameHistory;

    @Mock
    GameContext gameContext;

    @Mock
    Market market;

    @Mock
    FaithPath faithPath;

    @Mock
    DevelopmentCardsTable developmentCardsTable;

    Player player1 = new Player("first");
    Player player2 = new Player("second");
    Player player3 = new Player("third");

    List<Player> playersInOrder = List.of(player1, player2, player3);

    @Mock
    PlayerContext playerContext1;

    @Mock
    PlayerContext playerContext2;

    @Mock
    PlayerContext playerContext3;

    Map<Player, Set<ResourceStorage>> shelvesForPlayers;

    Set<GameUpdate> mockUpdates;

    @BeforeEach
    void gameStateTestsSetUp() {

        when(gameManager.getGameItemsManager()).thenReturn(gameItemsManager);
        lenient().when(gameManager.getGameHistory()).thenReturn(gameHistory);

        when(gameManager.getGameContext()).thenReturn(gameContext);
        lenient().when(gameContext.getMarket()).thenReturn(market);
        lenient().when(gameContext.getFaithPath()).thenReturn(faithPath);
        lenient().when(gameContext.getDevelopmentCardsTable()).thenReturn(developmentCardsTable);

        lenient().when(gameContext.getPlayerContext(eq(player1))).thenReturn(playerContext1);
        lenient().when(gameContext.getPlayerContext(eq(player2))).thenReturn(playerContext2);
        lenient().when(gameContext.getPlayerContext(eq(player3))).thenReturn(playerContext3);

        shelvesForPlayers = Map.of(
            player1, TestUtils.generateSetOfMockWithID(ResourceStorage.class, 3),
            player2, TestUtils.generateSetOfMockWithID(ResourceStorage.class, 3),
            player3, TestUtils.generateSetOfMockWithID(ResourceStorage.class, 3)
        );

        lenient().when(playerContext1.getShelves()).thenReturn(shelvesForPlayers.get(player1));
        lenient().when(playerContext1.getResourceStoragesForResourcesFromMarket()).thenReturn(shelvesForPlayers.get(player1));

        lenient().when(playerContext2.getShelves()).thenReturn(shelvesForPlayers.get(player2));
        lenient().when(playerContext2.getResourceStoragesForResourcesFromMarket()).thenReturn(shelvesForPlayers.get(player2));

        lenient().when(playerContext3.getShelves()).thenReturn(shelvesForPlayers.get(player3));
        lenient().when(playerContext3.getResourceStoragesForResourcesFromMarket()).thenReturn(shelvesForPlayers.get(player3));

        mockUpdates = Set.of(
            mock(GameUpdate.class),
            mock(GameUpdate.class),
            mock(GameUpdate.class),
            mock(GameUpdate.class)
        );
        lenient().when(gameManager.getAllGameUpdates()).thenReturn(mockUpdates);

    }

    void verifyThereIsAValidServerMessageForEveryPlayer(Map<Player, ServerMessage> answerServerMessages) {
        for(Player player : playersInOrder) {
            assertTrue(answerServerMessages.containsKey(player));
            assertFalse(answerServerMessages.get(player) instanceof InvalidRequestServerMessage);
        }
    }

    void verifyMessageContainsAllGameUpdates(GameUpdateServerMessage message) {
        assertEquals(mockUpdates, message.gameUpdates);
    }

    void verifyThatEveryPlayerGetsAllGameUpdates(Map<Player, ServerMessage> answerServerMessages) {
        for(Player player : playersInOrder) {
            assertTrue(answerServerMessages.containsKey(player));
            assertTrue(answerServerMessages.get(player) instanceof GameUpdateServerMessage);
            verifyMessageContainsAllGameUpdates((GameUpdateServerMessage) answerServerMessages.get(player));
        }
    }

    <T extends GameAction> void verifyGameHistoryActionAdded(Class<T> actionType) {
        ArgumentCaptor<GameAction> gameActionCaptor = ArgumentCaptor.forClass(GameAction.class);
        verify(gameHistory, atLeastOnce()).addAction(gameActionCaptor.capture());
        GameAction action = gameActionCaptor.getValue();
        assertEquals(actionType, action.getClass());
    }

    void verifyServerMessageIsAnswerForInvalidRequest(
        Player playerThatSentTheInvalidRequest,
        Map<Player, ServerMessage> answerServerMessages
    ) {

        //When the server receives an invalid request, only the sender should be notified
        assertEquals(Set.of(playerThatSentTheInvalidRequest), answerServerMessages.keySet());

        assertTrue(answerServerMessages.get(playerThatSentTheInvalidRequest) instanceof InvalidRequestServerMessage);

    }

}
