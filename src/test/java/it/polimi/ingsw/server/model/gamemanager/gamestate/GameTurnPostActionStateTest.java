package it.polimi.ingsw.server.model.gamemanager.gamestate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

class GameTurnPostActionStateTest extends GameStateTest{

    GameTurnPostActionState state;

    @BeforeEach
    void setUp(){

        when(gameContext.getActivePlayer()).thenReturn(player1);

        state = new GameTurnPostActionState(gameManager);
    }

    @Test
    void testHandleRequestEndTurn(){

//        assertFalse(state.isStateDone());
//        Map<Player, GameUpdateServerMessage> initialMessagesFromServer = state.getInitialServerMessage();
//        ServerMessage messageForPlayer = initialMessagesFromServer.get(player1);
//        assertFalse(state.isStateDone());
//
//        EndTurnClientRequest request = new EndTurnClientRequest(player1);
//        Map<Player, ServerMessage> handleRequestEndTurn = state.handleRequestEndTurn(request);
//
//        assertTrue(state.isStateDone());
//
//        verifyGameHistoryActionAdded(PostTurnFinalAction.class);
//
//    verifyThatEveryPlayerGetsAllGameUpdates(player1, )
    }

}