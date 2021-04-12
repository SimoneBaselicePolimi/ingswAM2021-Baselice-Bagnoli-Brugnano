package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.clientrequest.InitialChoicesClientRequest;
import it.polimi.ingsw.network.clientrequest.LeaderActionClientRequest;
import it.polimi.ingsw.network.clientrequest.MarketActionClientRequest;
import it.polimi.ingsw.network.clientrequest.ManageResourcesFromMarketClientRequest;
import it.polimi.ingsw.network.clientrequest.CustomClientRequest;
import it.polimi.ingsw.network.clientrequest.DevelopmentActionClientRequest;
import it.polimi.ingsw.network.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.network.clientrequest.EndTurnClientRequest;
import it.polimi.ingsw.network.servermessage.*;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.HashMap;
import java.util.Map;

public abstract class GameState<I extends ServerMessage, F extends ServerMessage> {

	protected final GameManager gameManager;

	protected GameState(GameManager gameManager) {
		this.gameManager = gameManager;
	}

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

	public Map<Player, ServerMessage> handleInitialChoiceCR(InitialChoicesClientRequest request) {
		return null;
	}

	public Map<Player, LeaderActionServerMessage> handleRequestLeaderAction(LeaderActionClientRequest request) {
		return null;
	}

	public Map<Player, MarketActionServerMessage> handleRequestMarketAction(MarketActionClientRequest request) {
		return null;
	}

	public Map<Player, ServerMessage> handleRequestManageResourcesFromMarket(ManageResourcesFromMarketClientRequest request) {
		return null;
	}

	public Map<Player, ServerMessage> handleRequestCustom(CustomClientRequest request) {
		return null;
	}

	public Map<Player, DevelopmentActionServerMessage> handleRequestDevelopmentAction(DevelopmentActionClientRequest request) {
		return null;
	}

	public Map<Player, ProductionActionServerMessage> handleRequestProductionAction(ProductionActionClientRequest request) {
		return null;
	}

	public Map<Player, EndTurnServerMessage> handleRequestEndTurn(EndTurnClientRequest request) {
		return null;
	}

}
