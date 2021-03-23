package it.polimi.ingsw.model.gamemanager.gamestate;

import it.polimi.ingsw.model.gamecontext.GameContext;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.clientrequest.ClientRequestLeaderAction;
import it.polimi.ingsw.network.clientrequest.ClientRequestEndTurn;

public class GameTurnPostActionState extends GameState {

	public void GameTurnPostActionState(GameContext gameContext, Player player) {

	}

	public Map<Player,ServerMessage> getInitialServerMessage() {
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

	public Map<Player,ServerMessageLeaderAction> handleRequestLeaderAction(ClientRequestLeaderAction request) {
		return null;
	}

	public Map<Player,ServerMessageEndTurn> handleRequestEndTurn(ClientRequestEndTurn request) {
		return null;
	}

}
