package it.polimi.ingsw.server.model.gamemanager;

import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameSetupState;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.Lobby;
import it.polimi.ingsw.network.clientrequest.ClientRequest;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.notifier.Notifier;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GameManager {

	private GameState currentState;

	private Set<Player> players;

	private ServerController controller;

	private Lobby lobby;

	private GameContext gameContext;

	private GameItemsManager gameItemsManager;

	private GameRules gameRules;

	private Set<Notifier> notifiers =  new HashSet<>();

	private GameHistory gameHistory;

	public GameManager(
	    Set<Player> players,
		ServerController controller,
		Lobby lobby,
		GameContext gameContext,
		GameItemsManager gameItemsManager,
		GameRules gameRules,
		GameHistory gameHistory
	) {
		this.players = players;
		this.controller = controller;
		this.lobby = lobby;
		this.gameContext = gameContext;
		this.gameItemsManager = gameItemsManager;
		this.gameRules = gameRules;
		this.gameHistory = gameHistory;
		changeState();
	}

	public void registerNotifier(Notifier notifier) {
		notifiers.add(notifier);
	}

	/**
	 * Side effects!!!
	 * @return
	 */
	public Set<GameUpdate> getAllGameUpdates() {
		return notifiers.stream()
			.flatMap(notifier -> notifier.getUpdates().stream())
			.collect(Collectors.toSet());
	}

	public GameHistory getGameHistory() {
		return gameHistory;
	}

	public Set<Player> getPlayers() {
		return new HashSet<>(players);
	}

	public GameContext getGameContext() {
		return gameContext;
	}

	public GameRules getGameRules() {
		return gameRules;
	}

	public GameItemsManager getGameItemsManager() {
		return gameItemsManager;
	}

	@SuppressWarnings("unchecked")
	public Map<Player, ServerMessage> handleClientRequest(ClientRequest request) throws ResourceStorageRuleViolationException, LeaderCardRequirementsNotSatisfiedException, ForbiddenPushOnTopException, NotEnoughResourcesException {
		ClientRequestValidator validator = request.getValidator();
		Optional<InvalidRequestServerMessage> error = validator.getErrorMessage(request, this);
		if(error.isPresent())
			return Map.of(request.player, error.get());
		else
			return request.callHandler(currentState);
	}

	private void changeState() {
		if (currentState == null)
			currentState = getInitialGameState();
		else {
			controller.sendMessagesToClients(currentState.getFinalServerMessage());
			currentState = currentState.getNextState();
		}
		controller.sendMessagesToClients(currentState.getInitialServerMessage());
	}

	private GameState getInitialGameState() {
		return new GameSetupState(this);
	}

}
