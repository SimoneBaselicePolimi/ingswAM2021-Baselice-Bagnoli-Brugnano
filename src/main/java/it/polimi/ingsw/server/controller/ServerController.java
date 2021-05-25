package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;

import java.util.Map;

public class ServerController {

	private GameManager gameManager;

	public void sendMessagesToClients(Map<Player, ServerMessage> messages) {

	}

	public void handleClientMessage(SocketMessage message) {

	}

}
