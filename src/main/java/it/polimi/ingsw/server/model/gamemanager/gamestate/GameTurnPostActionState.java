package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.clientrequest.LeaderActionClientRequest;
import it.polimi.ingsw.network.clientrequest.EndTurnClientRequest;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.network.servermessage.EndTurnServerMessage;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Map;

public class GameTurnPostActionState extends GameState {

    public GameTurnPostActionState(GameManager gameManager) {
        super(gameManager);
    }

    public void GameTurnPostActionState(GameContext gameContext, Player player) {

	}

	public Map<Player, ServerMessage> getInitialServerMessage() {
		return null;
	}

	public Map<Player,ServerMessage> getFinalServerMessage() {
		return null;
	}

	public boolean isStateDone() {
		return false;
	}

	public GameState getNextState() {
		return null;
	}

	public Map<Player, ServerMessage> handleRequestLeaderAction(LeaderActionClientRequest request) {
		return null;
	}

	public Map<Player, EndTurnServerMessage> handleRequestEndTurn(EndTurnClientRequest request) {
		return null;
	}

}
