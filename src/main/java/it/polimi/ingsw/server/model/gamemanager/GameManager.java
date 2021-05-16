package it.polimi.ingsw.server.model.gamemanager;

import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.GameContextBuilder;
import it.polimi.ingsw.server.model.gamecontext.GameContextCreationError;
import it.polimi.ingsw.server.model.gamecontext.ObservableGameContextBuilder;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameSetupState;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.network.clientrequest.ClientRequest;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.notifier.GameHistoryNotifier;
import it.polimi.ingsw.server.model.notifier.Notifier;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.utils.FileManager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GameManager {

	private GameState currentState;

	private Set<Player> players;

	private ServerController controller;

	private GameContext gameContext;

	private GameItemsManager gameItemsManager;

	private GameRules gameRules;

	private Set<Notifier> notifiers =  new HashSet<>();

	private GameHistory gameHistory;

	private ProjectLogger logger = ProjectLogger.getLogger();

	/**
	 * GameManager constructor
	 * @param players players of the game
	 * @param controller, see @link ServerController}
	 * @param gameRulesPath
	 * @throws InvalidGameRules
	 * @throws GameContextCreationError
	 */
	public GameManager(
	    Set<Player> players,
		ServerController controller,
		String gameRulesPath
	) throws InvalidGameRules, GameContextCreationError {

		logger.log(LogLevel.INFO, "GameManager initialization started for game with players %s.", players);

		this.players = players;
		this.controller = controller;
		this.gameItemsManager = new GameItemsManager();
		this.gameHistory = new GameHistoryNotifier();
		this.gameRules = readGameRulesFromFiles(gameRulesPath);

		GameContextBuilder contextBuilder = new ObservableGameContextBuilder(players, gameRules, gameItemsManager);
		this.gameContext = contextBuilder.build();

		changeState();
	}


	public void registerNotifier(Notifier notifier) {
		notifiers.add(notifier);
	}

	/**
	 * Side effects!!!
	 * @return
	 */
	public Set<ServerGameUpdate> getAllGameUpdates() {
		return notifiers.stream()
			.flatMap(notifier -> notifier.getUpdates().stream())
			.collect(Collectors.toSet());
	}

	/**
	 * Method to get the Game History
	 * @return GameHistory, see {@link GameHistory}
	 */
	public GameHistory getGameHistory() {
		return gameHistory;
	}

	/**
	 * Method to get all the players of the game
	 * @return returns the set of players in the game
	 */
	public Set<Player> getPlayers() {
		return new HashSet<>(players);
	}

	/**
	 * Method to get the Game Context
	 * @return GameContext, see {@link GameContext}
	 */
	public GameContext getGameContext() {
		return gameContext;
	}

	/**
	 * Method to get the Game Rules
	 * @return GameContext, see {@link GameRules}
	 */
	public GameRules getGameRules() {
		return gameRules;
	}

	/**
	 * Method to get the Game Items Manager
	 * @return GameItemsManager, see {@link GameItemsManager}
	 */
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

	private GameRules readGameRulesFromFiles(String gameRulesPath) throws InvalidGameRules {

		FileManager fileManager = FileManager.getFileManagerInstance();

		if (gameRulesPath != null && !gameRulesPath.isEmpty())
			gameRulesPath = FileManager.DEFAULT_RULES_PATH;

		try {
			return fileManager.getGameRules(gameRulesPath);
		} catch (IOException e) {
			String errorMessage;
			if(FileManager.DEFAULT_RULES_PATH.equals(gameRulesPath))
				errorMessage = String.format(
					"INVALID GAME RULES CONFIG. An exception was thrown while deserializing default game rules " +
						"(path: '%s').",
					FileManager.DEFAULT_RULES_PATH
				);
			else
				errorMessage = String.format(
					"INVALID GAME RULES CONFIG. An exception was thrown while deserializing game rules with path '%s'.",
					gameRulesPath
				);
			InvalidGameRules newException = new InvalidGameRules(errorMessage, e);
			logger.log(newException);
			throw newException;
		}

	}

	/**
	 * Method to change the current game state. If the game has not yet started, the method creates the new game.
	 */
	private void changeState() {
		if (currentState == null)
			currentState = getInitialGameState();
		else {
			controller.sendMessagesToClients(currentState.getFinalServerMessage());
			currentState = currentState.getNextState();
		}
		controller.sendMessagesToClients(currentState.getInitialServerMessage());
	}

	/**
	 * Method that returns the setup state of the game. The method is called when a new game is started.
	 * @return GameSetupState, see {@link GameSetupState}
	 */
	private GameSetupState getInitialGameState() {
		return new GameSetupState(this);
	}

}
