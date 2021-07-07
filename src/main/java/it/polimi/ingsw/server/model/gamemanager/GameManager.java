package it.polimi.ingsw.server.model.gamemanager;

import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.clientrequest.ClientRequest;
import it.polimi.ingsw.server.controller.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.server.controller.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.*;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.gamehistory.GameHistoryImp;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameSetupState;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.server.model.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.observableproxy.GameHistoryObservableProxy;
import it.polimi.ingsw.server.model.observableproxy.ObservableProxy;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.utils.FileManager;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GameManager {

	final private String gameID;

	private GameState currentState;

	private Set<Player> players;

	private GameController controller;

	private GameContext gameContext;

	private Optional<SinglePlayerGameContext> singlePlayerGameContext;

	private GameItemsManager gameItemsManager;

	private GameRules gameRules;

	private Set<ObservableProxy<?>> observableProxies =  new HashSet<>();

	private GameHistory gameHistory;

	private ProjectLogger logger = ProjectLogger.getLogger();

	/**
	 * GameManager constructor
     * @param gameID
	 * @param players players of the game
	 * @param controller, see {@link GameController}
	 * @param gameRulesPath
	 * @throws InvalidGameRules
	 * @throws GameContextCreationError
	 */
	public GameManager(
		String gameID,
		Set<Player> players,
		GameController controller,
		String gameRulesPath
	) throws InvalidGameRules, GameContextCreationError {
		this.gameID = gameID;

		logger.log(LogLevel.INFO, "GameManager initialization started for game with players %s.", players);

		this.players = players;
		this.controller = controller;
		this.gameItemsManager = new GameItemsManager();
		this.gameHistory = new GameHistoryObservableProxy(new GameHistoryImp(), this);
		this.gameRules = readGameRulesFromFiles(gameRulesPath);

		players.forEach(p -> gameItemsManager.addItem(p));

		GameContextBuilder contextBuilder = new ObservableGameContextBuilder(this, players, gameRules, gameItemsManager, gameHistory);
		if (players.size() == 1) {
			this.singlePlayerGameContext = Optional.of(contextBuilder.buildSinglePlayerGameContext());
			this.gameContext = this.singlePlayerGameContext.get();
		} else {
			this.singlePlayerGameContext = Optional.empty();
			this.gameContext = contextBuilder.buildGameContext();
		}

	}

	public boolean singlePlayerMode() {
		return singlePlayerGameContext.isPresent();
	}

	public void registerObservableProxy(ObservableProxy observableProxy) {
		observableProxies.add(observableProxy);
	}

	/**
	 * Side effects!!!
	 * @return
	 */
	public Set<ServerGameUpdate> getAllGameUpdates() {
		return observableProxies.stream()
			.map(ObservableProxy::getUpdates)
			.flatMap(Collection::stream)
			.collect(Collectors.toSet());
	}

	/**
	 * Method to get the Game History
	 * @return GameHistory, see {@link GameHistoryImp}
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
	 * @return GameContext, see {@link GameContextImp}
	 */
	public GameContext getGameContext() {
		return gameContext;
	}

	public Optional<SinglePlayerGameContext> getSinglePlayerGameContext() {
		return singlePlayerGameContext;
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

	public void startGame() {
		changeState();
	}

	@SuppressWarnings("unchecked")
	public void handleClientRequest(ClientRequest request)
			throws ResourceStorageRuleViolationException,
			LeaderCardRequirementsNotSatisfiedException,
			ForbiddenPushOnTopException,
			NotEnoughResourcesException {
		ClientRequestValidator validator = request.getValidator();
		Optional<InvalidRequestServerMessage> error = validator.getErrorMessage(request, this);
		if(error.isPresent()) {
			controller.sendMessagesToClients(Map.of(request.player, error.get()));
		} else {
			controller.sendMessagesToClients(request.callHandler(currentState));
			while (currentState.isStateDone())
				changeState();
		}
	}

	private GameRules readGameRulesFromFiles(String gameRulesPath) throws InvalidGameRules {

		FileManager fileManager = FileManager.getFileManagerInstance();

		if (gameRulesPath == null || gameRulesPath.isEmpty())
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
	 * Method to change the current game state. If the game has not started yet, the method creates the new game.
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
