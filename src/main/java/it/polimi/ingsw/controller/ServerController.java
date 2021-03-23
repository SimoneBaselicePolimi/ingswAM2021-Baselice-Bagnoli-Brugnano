package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gamemanager.GameManager;
import it.polimi.ingsw.network.servermessage.ServerMessage;

import java.util.Map;

public class ServerController {

	private GameManager gameManager;

	public void sendMessagesToClients(Map<Player, ServerMessage> messages) {

	}

	public void handleClientMessage(SocketMessage message) {

	}

}
