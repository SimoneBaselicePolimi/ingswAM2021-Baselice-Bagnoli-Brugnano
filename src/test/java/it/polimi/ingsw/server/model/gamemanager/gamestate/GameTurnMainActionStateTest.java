package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.network.clientrequest.*;
import it.polimi.ingsw.network.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.network.servermessage.InitialChoicesServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamehistory.*;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
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

        verifyThatEveryPlayerGetsAllGameUpdates(Map.of(player1, messageForPlayer));

        verifyGameHistoryActionAdded(ObtainedMarblesMarketAction.class);

        assertTrue(state.isStateDone());
        assertTrue(state.getNextState() instanceof ManageResourcesFromMarketState);

        //Send duplicated request
        Map<Player, ServerMessage> duplicatedRequestAnswerServerMessages = state.handleRequestFetchColumnMarketAction(
            new MarketActionFetchColumnClientRequest(player1, 2)
        );

        verifyServerMessageIsAnswerForInvalidRequest(player1, duplicatedRequestAnswerServerMessages);

        //Send not active player request
        Map<Player, ServerMessage> notActiveTurnRequestAnswerServerMessages = state.handleRequestFetchColumnMarketAction(
            new MarketActionFetchColumnClientRequest(player2, 2)
        );

        verifyServerMessageIsAnswerForInvalidRequest(player2, notActiveTurnRequestAnswerServerMessages);

    }

    @Test
    void testHandleRequestFetchRowMarketAction() throws ResourceStorageRuleViolationException, NotEnoughResourcesException {

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

        verifyThatEveryPlayerGetsAllGameUpdates(Map.of(player1, messageForPlayer));

        verifyGameHistoryActionAdded(ObtainedMarblesMarketAction.class);

        assertTrue(state.isStateDone());
        assertTrue(state.getNextState() instanceof ManageResourcesFromMarketState);

        //Send duplicated request
        Map<Player, ServerMessage> duplicatedRequestAnswerServerMessages = state.handleRequestFetchRowMarketAction(
            new MarketActionFetchRowClientRequest(player1, 1)
        );

        verifyServerMessageIsAnswerForInvalidRequest(player1, duplicatedRequestAnswerServerMessages);

        //Send not active player request
        Map<Player, ServerMessage> notActiveTurnRequestAnswerServerMessages = state.handleRequestFetchRowMarketAction(
            new MarketActionFetchRowClientRequest(player2, 1)
        );

        verifyServerMessageIsAnswerForInvalidRequest(player2, notActiveTurnRequestAnswerServerMessages);

    }

    @Test
    void testHandleRequestDevelopmentAction() throws ForbiddenPushOnTopException {

        assertFalse(state.isStateDone());
        Map<Player, GameUpdateServerMessage> initialMessagesFromServer = state.getInitialServerMessage();
        assertFalse(state.isStateDone());
        ServerMessage messageForPlayer = initialMessagesFromServer.get(player1);

        //valid request
        Map<Player, ServerMessage> request = state.handleRequestDevelopmentAction(
            new DevelopmentActionClientRequest(player1, developmentCard, 1)
        );

        verify(developmentCardsTable).popCard(developmentCard.getLevel(), developmentCard.getColour());
        verify(playerDeck).pushOnTop(developmentCard);
        verify(playerContext1.getShelves().remove(developmentCard.getPurchaseCost()));

        verifyThatEveryPlayerGetsAllGameUpdates(Map.of(player1, messageForPlayer));

        verifyGameHistoryActionAdded(DevelopmentAction.class);

        assertTrue(state.isStateDone());
        assertTrue(state.getNextState() instanceof GameTurnPostActionState);

        //Send duplicated request
        Map<Player, ServerMessage> duplicatedRequestAnswerServerMessages = state.handleRequestDevelopmentAction(
            new DevelopmentActionClientRequest(player1, developmentCard, 1)
        );

        verifyServerMessageIsAnswerForInvalidRequest(player1, duplicatedRequestAnswerServerMessages);
    }

    @Mock
    Production production1;

    @Mock
    Production production2;

    @Test
    void testHandleRequestProductionAction() throws NotEnoughResourcesException, ResourceStorageRuleViolationException {

        lenient().when(playerContext1.getActiveProductions()).thenReturn(Set.of(production1, production2));

        //Production1:
        //  Cost: 2 COINS
        //  ResourceReward: 1 STONES, 1 SHIELDS
        //  StarResourcesCost: 0
        //  StarResourcesReward: 0
        //  FaithPathReward: 2
        lenient().when(production1.getProductionResourceCost()).thenReturn(Map.of(ResourceType.COINS, 2));
        lenient().when(production1.getProductionResourceReward()).thenReturn(Map.of(ResourceType.STONES,1, ResourceType.SHIELDS, 1));
        lenient().when(production1.getProductionStarResourceCost()).thenReturn(0);
        lenient().when(production1.getProductionStarResourceReward()).thenReturn(0);
        lenient().when(production1.getProductionFaithReward()).thenReturn(2);

        //Production2:
        //  Cost: 0
        //  ResourceReward: 0
        //  StarResourcesCost: 2
        //  StarResourcesReward: 3
        //  FaithPathReward: 1
        lenient().when(production1.getProductionResourceCost()).thenReturn(new HashMap<>());
        lenient().when(production1.getProductionResourceReward()).thenReturn(new HashMap<>());
        lenient().when(production1.getProductionStarResourceCost()).thenReturn(2);
        lenient().when(production1.getProductionStarResourceReward()).thenReturn(3);
        lenient().when(production1.getProductionFaithReward()).thenReturn(1);

        assertFalse(state.isStateDone());
        Map<Player, GameUpdateServerMessage> initialMessagesFromServer = state.getInitialServerMessage();
        assertFalse(state.isStateDone());
        ServerMessage messageForPlayer = initialMessagesFromServer.get(player1);

        //valid request
        Map<Player, ServerMessage> request = state.handleRequestProductionAction(
            new ProductionActionClientRequest(
                player1,
                Set.of(production1, production2),
                Map.of(ResourceType.SHIELDS, 2),
                Map.of(ResourceType.STONES, 3)
            )
        );

        verify(playerContext1).getResourceStoragesForResourcesFromMarket();
        //verify()
        //verify(playerDeck).pushOnTop(developmentCard);
        verify(playerContext1.getShelves().remove(developmentCard.getPurchaseCost()));

        verifyThatEveryPlayerGetsAllGameUpdates(Map.of(player1, messageForPlayer));

        verifyGameHistoryActionAdded(ProductionAction.class);

        assertTrue(state.isStateDone());
        assertTrue(state.getNextState() instanceof GameTurnPostActionState);
    }


    @Mock
    LeaderCard leaderCardThePlayerWantsToDiscard;

    @Test
    void testDiscardLeaderCardAction() throws LeaderCardRequirementsNotSatisfiedException {

        when(leaderCardThePlayerWantsToDiscard.getState()).thenReturn(LeaderCardState.HIDDEN);

        assertFalse(state.isStateDone());
        //send a DiscardLeaderCardClientRequest
        Map<Player, GameUpdateServerMessage> initialMessagesFromServer = state.getInitialServerMessage();
        assertFalse(state.isStateDone());
        ServerMessage messageForPlayer = initialMessagesFromServer.get(player1);

        Map<Player, ServerMessage> discardCardRequest = state.handleRequestDiscardLeaderAction(
            new DiscardLeaderCardClientRequest(player1, leaderCardThePlayerWantsToDiscard)
        );

        verify(leaderCardThePlayerWantsToDiscard).discardLeaderCard();

        verifyThatEveryPlayerGetsAllGameUpdates(Map.of(player1, messageForPlayer));
        verifyGameHistoryActionAdded(DiscardLeaderCardsAction.class);

        assertFalse(state.isStateDone());

    }

    @Mock
    LeaderCard leaderCardThePlayerWantsToActivate;

    @Test
    void testActivateLeaderCardAction() throws LeaderCardRequirementsNotSatisfiedException {

        when(leaderCardThePlayerWantsToActivate.getState()).thenReturn(LeaderCardState.HIDDEN);
        when(leaderCardThePlayerWantsToActivate.areRequirementsSatisfied(playerContext1)).thenReturn(true);

        assertFalse(state.isStateDone());
        //send a ActivateLeaderCardClientRequest
        Map<Player, GameUpdateServerMessage> initialMessagesFromServer = state.getInitialServerMessage();
        assertFalse(state.isStateDone());
        ServerMessage messageForPlayer = initialMessagesFromServer.get(player1);

        Map<Player, ServerMessage> activateCardRequest = state.handleRequestActivateLeaderAction(
            new ActivateLeaderCardClientRequest(player1, leaderCardThePlayerWantsToActivate)
        );

        verify(leaderCardThePlayerWantsToActivate).activateLeaderCard(playerContext1);

        verifyThatEveryPlayerGetsAllGameUpdates(Map.of(player1, messageForPlayer));
        verifyGameHistoryActionAdded(ActivateLeaderCardsAction.class);

        assertFalse(state.isStateDone());

    }
}
