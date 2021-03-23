package it.polimi.ingsw.model.gamemanager.gamestate;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gamecontext.GameContext;
import it.polimi.ingsw.model.gamehistory.GameHistory;
import it.polimi.ingsw.network.clientrequest.InitialChoiceCR;
import it.polimi.ingsw.network.clientrequest.ClientRequestLeaderAction;
import it.polimi.ingsw.network.clientrequest.ClientRequestMarketAction;
import it.polimi.ingsw.network.clientrequest.ClientRequestManageResourcesFromMarket;
import it.polimi.ingsw.network.clientrequest.ClientRequestCustom;
import it.polimi.ingsw.network.clientrequest.ClientRequestDevelopmentAction;
import it.polimi.ingsw.network.clientrequest.ClientRequestProductionAction;
import it.polimi.ingsw.network.clientrequest.ClientRequestEndTurn;
import it.polimi.ingsw.network.servermessage.*;

import java.util.HashMap;
import java.util.Map;

public abstract class GameState<I extends ServerMessage, F extends ServerMessage> {

	private GameContext gameContext;

	private GameHistory gameHistory;

	/**
	 * The initial messages that should be sent to the clients when this GameState becomes the currentGameState
	 * @return the default implementation returns an empty map (no message will be sent)
	 */
	public Map<Player, I> getInitialServerMessage() {
		return new HashMap<>();
	}

	/**
	 * The final messages that should be sent to the clients before changing to a new GameState
	 * @return the default implementation returns an empty map (no message will be sent)
	 */
	public Map<Player, F> getFinalServerMessage() {
		return new HashMap<>();
	}

	public abstract boolean isStateDone();

	public abstract GameState getNextState();

	public Map<Player, ServerMessage> handleInitialChoiceCR(InitialChoiceCR request) {
		return null;
	}

	public Map<Player,ServerMessageLeaderAction> handleRequestLeaderAction(ClientRequestLeaderAction request) {
		return null;
	}

	public Map<Player, ServerMessageMarketAction> handleRequestMarketAction(ClientRequestMarketAction request) {
		return null;
	}

	public Map<Player, ServerMessage> handleRequestManageResourcesFromMarket(ClientRequestManageResourcesFromMarket request) {
		return null;
	}

	public Map<Player, ServerMessage> handleRequestCustom(ClientRequestCustom request) {
		return null;
	}

	public Map<Player, ServerMessageDevelopmentAction> handleRequestDevelopmentAction(ClientRequestDevelopmentAction request) {
		return null;
	}

	public Map<Player, ServerMessageProductionAction> handleRequestProductionAction(ClientRequestProductionAction request) {
		return null;
	}

	public Map<Player, ServerMessageEndTurn> handleRequestEndTurn(ClientRequestEndTurn request) {
		return null;
	}

}
