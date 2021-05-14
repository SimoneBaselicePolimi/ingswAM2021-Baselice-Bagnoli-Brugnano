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
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.testutils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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

    @Mock
    MarbleColour marble1;

    @Mock
    MarbleColour marble2;

    @Mock
    MarbleColour marble3;

    @Mock
    DevelopmentCardsTable table;

    List<DevelopmentCard> availableDevelopmentCards;

    Iterator<DevelopmentCard> iter;
    DevelopmentCard rightCardThirdLevel;
    DevelopmentCard rightCardSecondLevel;
    DevelopmentCard rightCardFirstLevel;

    @Mock
    PlayerOwnedDevelopmentCardDeck playerDeck;

    @BeforeEach
    void gameStateTestsSetUp() {

        lenient().when(gameManager.getGameItemsManager()).thenReturn(gameItemsManager);
        lenient().when(gameManager.getGameHistory()).thenReturn(gameHistory);

        lenient().when(gameManager.getGameContext()).thenReturn(gameContext);
        lenient().when(gameContext.getMarket()).thenReturn(market);
        lenient().when(gameContext.getFaithPath()).thenReturn(faithPath);
        lenient().when(gameContext.getDevelopmentCardsTable()).thenReturn(table);

        lenient().when(gameContext.getPlayersTurnOrder()).thenReturn(playersInOrder);
        lenient().when(gameContext.getPlayerContext(eq(player1))).thenReturn(playerContext1);
        lenient().when(gameContext.getPlayerContext(eq(player2))).thenReturn(playerContext2);
        lenient().when(gameContext.getPlayerContext(eq(player3))).thenReturn(playerContext3);

        //Matrix 2 x 3
        lenient().when(market.getNumOfColumns()).thenReturn(3);
        lenient().when(market.getNumOfRows()).thenReturn(3);

        lenient().when(market.fetchMarbleColumn(2)).thenReturn(marblesInColumn);
        lenient().when(market.fetchMarbleRow(1)).thenReturn(marblesInRow);

        lenient().when(marble1.isSpecialMarble()).thenReturn(false);
        lenient().when(marble2.isSpecialMarble()).thenReturn(true);
        lenient().when(marble3.isSpecialMarble()).thenReturn(false);

        lenient().when(marble1.getResourceType()).thenReturn(Optional.of(ResourceType.COINS));
        lenient().when(marble2.getResourceType()).thenReturn(Optional.of(ResourceType.COINS));
        lenient().when(marble3.getResourceType()).thenReturn(Optional.of(ResourceType.SERVANTS));

        lenient().when(marble1.getFaithPoints()).thenReturn(0);
        lenient().when(marble2.getFaithPoints()).thenReturn(1);
        lenient().when(marble3.getFaithPoints()).thenReturn(2);

        availableDevelopmentCards = TestUtils.generateListOfMockWithID(DevelopmentCard.class, 8);

        iter = availableDevelopmentCards.iterator();
        rightCardFirstLevel = iter.next();
        rightCardSecondLevel = iter.next();
        rightCardThirdLevel = iter.next();

        //The player has 3 COINS, 2 SERVANTS
        //Third Level Card Cost : 3 COINS, 1 STONES (not enough resources)
        //Second Level Card Cost : 1 COINS, 2 SERVANTS (enough resources)

        lenient().when(gameContext.getDevelopmentCardsTable()).thenReturn(table);
        lenient().when(table.getAvailableCards()).thenReturn(availableDevelopmentCards);
        lenient().when(table.popCard(eq(DevelopmentCardLevel.FIRST_LEVEL), eq(DevelopmentCardColour.YELLOW))).thenReturn(rightCardFirstLevel);
        lenient().when(rightCardFirstLevel.getLevel()).thenReturn(DevelopmentCardLevel.FIRST_LEVEL);
        lenient().when(rightCardFirstLevel.getColour()).thenReturn(DevelopmentCardColour.YELLOW);

        lenient().when(rightCardSecondLevel.getLevel()).thenReturn(DevelopmentCardLevel.SECOND_LEVEL);
        lenient().when(rightCardThirdLevel.getLevel()).thenReturn(DevelopmentCardLevel.THIRD_LEVEL);
        lenient().when(playerContext1.getAllResources()).thenReturn(Map.of(ResourceType.COINS, 3, ResourceType.SERVANTS, 2));

        lenient().when(rightCardThirdLevel.getPurchaseCost()).thenReturn(Map.of(ResourceType.COINS, 3, ResourceType.STONES, 1));
        lenient().when(rightCardSecondLevel.getPurchaseCost()).thenReturn(Map.of(ResourceType.COINS, 1, ResourceType.SERVANTS, 2));

        lenient().when(playerContext1.canAddDevelopmentCard(rightCardFirstLevel, 1)).thenReturn(false);
        lenient().when(playerContext1.canAddDevelopmentCard(rightCardSecondLevel, 1)).thenReturn(true);
        lenient().when(playerContext1.canAddDevelopmentCard(rightCardThirdLevel, 1)).thenReturn(true);

        shelvesForPlayers = Map.of(
            player1, TestUtils.generateSetOfMockWithID(ResourceStorage.class, 3),
            player2, TestUtils.generateSetOfMockWithID(ResourceStorage.class, 3),
            player3, TestUtils.generateSetOfMockWithID(ResourceStorage.class, 3)
        );

        lenient().when(playerContext1.getShelves()).thenReturn(shelvesForPlayers.get(player1));
        Set<ResourceStorage> totalStoragesStoragesForResourcesFromMarket = new HashSet<>();
        totalStoragesStoragesForResourcesFromMarket.addAll(shelvesForPlayers.get(player1));
        totalStoragesStoragesForResourcesFromMarket.addAll(leaderStoragesForPlayer1);
        lenient().when(playerContext1.getResourceStoragesForResourcesFromMarket()).thenReturn(
            totalStoragesStoragesForResourcesFromMarket
        );

        lenient().when(playerContext2.getShelves()).thenReturn(shelvesForPlayers.get(player2));
        lenient().when(playerContext2.getResourceStoragesForResourcesFromMarket()).thenReturn(shelvesForPlayers.get(player2));

        lenient().when(playerContext3.getShelves()).thenReturn(shelvesForPlayers.get(player3));
        lenient().when(playerContext3.getResourceStoragesForResourcesFromMarket()).thenReturn(shelvesForPlayers.get(player3));

        lenient().when(playerContext1.getAllResources()).thenReturn(Map.of(
            ResourceType.COINS, 10,
            ResourceType.SHIELDS, 10,
            ResourceType.STONES, 10,
            ResourceType.SERVANTS, 10
        ));

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
