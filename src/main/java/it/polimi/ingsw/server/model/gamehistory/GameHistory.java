package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class GameHistory {

	private List<GameAction> gameActions = new ArrayList<>();

	public List<GameAction> getGameHistory() {
		return new ArrayList<>(gameActions);
	}

	public void addAction(GameAction gameAction){
		gameActions.add(gameAction);
	}

}
