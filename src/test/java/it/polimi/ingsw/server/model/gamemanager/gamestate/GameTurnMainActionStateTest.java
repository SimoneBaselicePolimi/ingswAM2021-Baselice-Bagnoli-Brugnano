package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.network.clientrequest.*;
import it.polimi.ingsw.network.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamehistory.*;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.testutils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTurnMainActionStateTest extends GameStateTest {

    GameTurnMainActionState state;

    @BeforeEach
    void setUp(){

        when(gameContext.getActivePlayer()).thenReturn(player1);

        state = new GameTurnMainActionState(gameManager);
    }

    @Test
    void testInitialServerMessage() {

        Map<Player, GameUpdateServerMessage> serverMessages = state.getInitialServerMessage();
        verifyGameHistoryActionAdded(MainTurnInitialAction.class);
        assertFalse(state.isStateDone());
    }

    @Test
    void testHandleRequestFetchColumnMarketAction() throws ResourceStorageRuleViolationException, NotEnoughResourcesException {

        lenient().when(market.fetchMarbleColumn(eq(2))).thenReturn(new MarbleColour[]{marble1, marble2, marble3});

        assertFalse(state.isStateDone());
        Map<Player, GameUpdateServerMessage> initialMessagesFromServer = state.getInitialServerMessage();
        assertFalse(state.isStateDone());

        ServerMessage messageForPlayer = initialMessagesFromServer.get(player1);

        //valid request
        Map<Player, ServerMessage> request = state.handleRequestFetchColumnMarketAction(
            new MarketActionFetchColumnClientRequest(player1, 2)
        );

        verify(faithPath).move(player1, 3);
        verify(playerContext1).setTemporaryStorageResources(Map.of(ResourceType.COINS,2, ResourceType.SERVANTS, 1));
        verify(playerContext1).setTempStarResources(1);

        verifyThatEveryPlayerGetsAllGameUpdates(initialMessagesFromServer);

        verifyGameHistoryActionAdded(ObtainedMarblesMarketAction.class);

        assertTrue(state.isStateDone());
        assertTrue(state.getNextState() instanceof ManageResourcesFromMarketState);

        //Send not active player request
        Map<Player, ServerMessage> notActiveTurnRequestAnswerServerMessages = state.handleRequestFetchColumnMarketAction(
            new MarketActionFetchColumnClientRequest(player2, 2)
        );

        verifyServerMessageIsAnswerForInvalidRequest(player2, notActiveTurnRequestAnswerServerMessages);

    }

    @Test
    void testHandleRequestFetchRowMarketAction() throws ResourceStorageRuleViolationException, NotEnoughResourcesException {

        lenient().when(market.fetchMarbleRow(eq(1))).thenReturn(new MarbleColour[]{marble1, marble2});

        assertFalse(state.isStateDone());
        Map<Player, GameUpdateServerMessage> initialMessagesFromServer = state.getInitialServerMessage();
        assertFalse(state.isStateDone());
        ServerMessage messageForPlayer = initialMessagesFromServer.get(player1);

        //valid request
        Map<Player, ServerMessage> request = state.handleRequestFetchRowMarketAction(
            new MarketActionFetchRowClientRequest(player1, 1)
        );

        verify(faithPath).move(player1, 1);
        verify(playerContext1).setTemporaryStorageResources(Map.of(ResourceType.COINS,2));
        verify(playerContext1).setTempStarResources(1);

        verifyThatEveryPlayerGetsAllGameUpdates(initialMessagesFromServer);

        verifyGameHistoryActionAdded(ObtainedMarblesMarketAction.class);

        assertTrue(state.isStateDone());
        assertTrue(state.getNextState() instanceof ManageResourcesFromMarketState);

        //Send not active player request
        Map<Player, ServerMessage> notActiveTurnRequestAnswerServerMessages = state.handleRequestFetchRowMarketAction(
            new MarketActionFetchRowClientRequest(player2, 1)
        );

        verifyServerMessageIsAnswerForInvalidRequest(player2, notActiveTurnRequestAnswerServerMessages);

    }

    @Test
    void testHandleRequestDevelopmentAction() throws ForbiddenPushOnTopException, NotEnoughResourcesException {

        assertFalse(state.isStateDone());
        Map<Player, GameUpdateServerMessage> initialMessagesFromServer = state.getInitialServerMessage();
        assertFalse(state.isStateDone());
        ServerMessage messageForPlayer = initialMessagesFromServer.get(player1);

        //valid request
        Map<Player, ServerMessage> request = state.handleRequestDevelopmentAction(
            new DevelopmentActionClientRequest(player1, rightCardFirstLevel, 1)
        );

        verify(table).popCard(rightCardFirstLevel.getLevel(), rightCardFirstLevel.getColour());
        verify(playerContext1).addDevelopmentCard(eq(rightCardFirstLevel), eq(1));
        verify(playerContext1).removeResourcesBasedOnResourcesStoragesPriority(eq(rightCardFirstLevel.getPurchaseCost()));

        verifyThatEveryPlayerGetsAllGameUpdates(initialMessagesFromServer);

        verifyGameHistoryActionAdded(DevelopmentAction.class);

        assertTrue(state.isStateDone());
        assertTrue(state.getNextState() instanceof GameTurnPostActionState);
    }

    Set<Production> productionsThePlayerCanActivate = TestUtils.generateSetOfMockWithID(Production.class, 5);
    Iterator<Production> iter;
    Production production1;

    @Mock
    ResourceStorage shelvePlayer1;

    @Mock
    ResourceStorage chest;

    @Test
    void testHandleRequestProductionAction() throws NotEnoughResourcesException, ResourceStorageRuleViolationException {

        assertFalse(state.isStateDone());
        Map<Player, GameUpdateServerMessage> initialMessagesFromServer = state.getInitialServerMessage();
        assertFalse(state.isStateDone());
        ServerMessage messageForPlayer = initialMessagesFromServer.get(player1);

        //rightProduction1
        //- Cost: 2 SHIELDS
        //- StarResourceCost : 0
        //- StarResourceReward : 0

        //The player has 1 COINS, 5 SHIELDS, 3 STONES

        iter = productionsThePlayerCanActivate.iterator();
        production1 = iter.next();

        lenient().when(playerContext1.getShelves()).thenReturn(Set.of(shelvePlayer1));
        lenient().when(shelvePlayer1.canAddResources(any())).thenReturn(true);
        lenient().when(shelvePlayer1.canRemoveResources(any())).thenReturn(true);

        when(production1.getProductionStarResourceCost()).thenReturn(0);
        when(production1.getProductionStarResourceReward()).thenReturn(0);
        when(playerContext1.getInfiniteChest()).thenReturn(chest);
        when(production1.getProductionResourceCost()).thenReturn(Map.of(ResourceType.SHIELDS, 2));
        when(production1.getProductionResourceReward()).thenReturn(Map.of(ResourceType.COINS, 2, ResourceType.STONES, 1));
        when (production1.getProductionFaithReward()).thenReturn(1);
        lenient().when(playerContext1.getActiveProductions()).thenReturn(productionsThePlayerCanActivate);
        lenient().when(playerContext1.getAllResources()).thenReturn(Map.of(ResourceType.COINS, 1, ResourceType.SHIELDS, 5, ResourceType.STONES,3));

        //valid request
        Map<Player, ServerMessage> request = state.handleRequestProductionAction(
            new ProductionActionClientRequest(
                player1,
                Set.of(production1),
                new HashMap<>(),
                new HashMap<>()
            )
        );

        verify(playerContext1).removeResourcesBasedOnResourcesStoragesPriority(eq(production1.getProductionResourceCost()));
        verify(chest).addResources(production1.getProductionResourceReward());
        verify(faithPath).move(player1, 1);
        verifyThatEveryPlayerGetsAllGameUpdates(initialMessagesFromServer);

        verifyGameHistoryActionAdded(ProductionAction.class);

        assertTrue(state.isStateDone());
        assertTrue(state.getNextState() instanceof GameTurnPostActionState);
    }


    @Mock
    LeaderCard leaderCardThePlayerWantsToDiscard;

    @Test
    void testDiscardLeaderCardAction() throws LeaderCardRequirementsNotSatisfiedException {

        lenient().when(leaderCardThePlayerWantsToDiscard.getState()).thenReturn(LeaderCardState.HIDDEN);

        assertFalse(state.isStateDone());
        //send a DiscardLeaderCardClientRequest
        Map<Player, GameUpdateServerMessage> initialMessagesFromServer = state.getInitialServerMessage();
        assertFalse(state.isStateDone());
        ServerMessage messageForPlayer = initialMessagesFromServer.get(player1);

        Map<Player, ServerMessage> discardCardRequest = state.handleRequestDiscardLeaderAction(
            new DiscardLeaderCardClientRequest(player1, leaderCardThePlayerWantsToDiscard)
        );

        verify(leaderCardThePlayerWantsToDiscard).discardLeaderCard();

        verifyThatEveryPlayerGetsAllGameUpdates(initialMessagesFromServer);
        verifyGameHistoryActionAdded(DiscardLeaderCardsAction.class);

        assertFalse(state.isStateDone());

    }

    @Mock
    LeaderCard leaderCardThePlayerWantsToActivate;

    @Test
    void testActivateLeaderCardAction() throws LeaderCardRequirementsNotSatisfiedException {

        lenient().when(leaderCardThePlayerWantsToActivate.getState()).thenReturn(LeaderCardState.HIDDEN);
        lenient().when(leaderCardThePlayerWantsToActivate.areRequirementsSatisfied(playerContext1)).thenReturn(true);

        assertFalse(state.isStateDone());
        //send a ActivateLeaderCardClientRequest
        Map<Player, GameUpdateServerMessage> initialMessagesFromServer = state.getInitialServerMessage();
        assertFalse(state.isStateDone());
        ServerMessage messageForPlayer = initialMessagesFromServer.get(player1);

        Map<Player, ServerMessage> activateCardRequest = state.handleRequestActivateLeaderAction(
            new ActivateLeaderCardClientRequest(player1, leaderCardThePlayerWantsToActivate)
        );

        verify(leaderCardThePlayerWantsToActivate).activateLeaderCard(playerContext1);

        verifyThatEveryPlayerGetsAllGameUpdates(initialMessagesFromServer);
        verifyGameHistoryActionAdded(ActivateLeaderCardsAction.class);

        assertFalse(state.isStateDone());

    }
}
