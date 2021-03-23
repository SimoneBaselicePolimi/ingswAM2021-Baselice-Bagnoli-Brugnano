package it.polimi.ingsw.model.gamemanager;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.network.clientrequest.ClientRequest;
import it.polimi.ingsw.network.servermessage.ServerMessage;

import java.util.Map;

public class GameManager {

	private GameState currentState;

	private ServerController controller;

	private Lobby lobby;

	public GameManager(Lobby lobby) {

	}

	public Map<Player, ServerMessage> handleClientRequest(ClientRequest request) {
		return null;
	}

	private void changeState() {

	}

	private GameState getInitialGameState() {
		return null;
	}

}
