package it.polimi.ingsw.server.model.gamemanager;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
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

	public GameManager(Lobby lobby, ServerController controller, GameContext gameContext) {
		this.lobby = lobby;
		this.controller = controller;
		this.gameContext = gameContext;
		changeState();
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
		return new GameSetupState(gameContext, );
	}

}
