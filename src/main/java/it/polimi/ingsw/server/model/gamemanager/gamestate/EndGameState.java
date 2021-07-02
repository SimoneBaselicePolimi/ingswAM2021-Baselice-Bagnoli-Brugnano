package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.controller.servermessage.EndGameServerMessage;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EndGameState extends GameState {

	protected EndGameState(GameManager gameManager) {
		super(gameManager);
	}

	@Override
	public Map getInitialServerMessage() {
		return gameManager.getPlayers().stream()
			.collect(
				Collectors.toMap(Function.identity(),
					player ->  new EndGameServerMessage()
				)
			);
	}

	@Override
	public boolean isStateDone() {
		return false;
	}

	@Override
	public GameState getNextState() {
		return null;
	}
}
