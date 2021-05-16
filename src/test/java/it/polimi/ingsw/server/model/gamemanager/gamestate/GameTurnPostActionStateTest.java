package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.network.clientrequest.EndTurnClientRequest;
import it.polimi.ingsw.network.servermessage.EndTurnServerMessage;
import it.polimi.ingsw.network.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamehistory.PostTurnFinalAction;
import it.polimi.ingsw.server.model.gamehistory.SetupStartedAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
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