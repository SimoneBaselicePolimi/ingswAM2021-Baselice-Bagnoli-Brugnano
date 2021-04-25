package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.network.clientrequest.*;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.servermessage.*;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

	public Map<Player, ServerMessage> handleInitialChoiceCR(InitialChoicesClientRequest request) throws ResourceStorageRuleViolationException {
		return null;
	}

	public Map<Player, ServerMessage> handleRequestLeaderAction(DiscardLeaderCardClientRequest request) throws LeaderCardRequirementsNotSatisfiedException {
		return null;
	}

	public Map<Player, GameUpdateServerMessage> handleRequestLeaderAction(ActivateLeaderCardClientRequest request) throws LeaderCardRequirementsNotSatisfiedException {
		return null;
	}

	public Map<Player, ServerMessage> handleRequestFetchColumnMarketAction(MarketActionFetchColumnClientRequest request) {
		return null;
	}

	public Map<Player, ServerMessage> handleRequestFetchRowMarketAction(MarketActionFetchRowClientRequest request) {
		return null;
	}

	public Map<Player, ServerMessage> handleRequestManageResourcesFromMarket(ManageResourcesFromMarketClientRequest request) {
		return null;
	}

	public Map<Player, ServerMessage> handleRequestCustom(CustomClientRequest request) {
		return null;
	}

	public Map<Player, ServerMessage> handleRequestDevelopmentAction(DevelopmentActionClientRequest request) throws ForbiddenPushOnTopException {
		return null;
	}

	public Map<Player, ServerMessage> handleRequestProductionAction(ProductionActionClientRequest request) throws NotEnoughResourcesException, ResourceStorageRuleViolationException {
		return null;
	}
	//TODO

	public Map<Player, ServerMessage> handleRequestEndTurn(EndTurnClientRequest request) {
		return null;
	}

	protected Map<Player, GameUpdateServerMessage> buildGameUpdateServerMessage() {
		Set<GameUpdate> gameUpdates = gameManager.getAllGameUpdates();
		Map<Player, GameUpdateServerMessage> serverMessages = new HashMap<>();
		for (Player player : gameManager.getPlayers())
			serverMessages.put(player, new GameUpdateServerMessage(gameUpdates));
		return serverMessages;
	}


	protected static Map<Player, ServerMessage> createInvalidRequestServerMessage(
		Player requestSender,
		String errorMessage,
		Object... messageArgs
	) {
		InvalidRequestServerMessage serverMessage = new InvalidRequestServerMessage(
			String.format(errorMessage, messageArgs)
		);
		return Map.of(requestSender, serverMessage);
	}

}
