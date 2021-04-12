package it.polimi.ingsw.server.model.gamemanager;

import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameSetupState;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.Lobby;
import it.polimi.ingsw.network.clientrequest.ClientRequest;
import it.polimi.ingsw.network.servermessage.ServerMessage;

import java.util.Map;

public class GameManager {

	private GameState currentState;

	private ServerController controller;

	private Lobby lobby;

	private GameContext gameContext;

	private GameItemsManager gameItemsManager;

	private GameRules gameRules;

	public GameManager(
		ServerController controller,
		Lobby lobby,
		GameContext gameContext,
		GameItemsManager gameItemsManager,
		GameRules gameRules
	) {
		this.controller = controller;
		this.lobby = lobby;
		this.gameContext = gameContext;
		this.gameItemsManager = gameItemsManager;
		this.gameRules = gameRules;
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

	public Map<Player, ServerMessage> handleClientRequest(ClientRequest request) {
		request.callHandler(currentState);
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
