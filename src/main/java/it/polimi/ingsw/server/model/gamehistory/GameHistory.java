package it.polimi.ingsw.server.model.gamehistory;

import java.util.ArrayList;
import java.util.List;

public class GameHistory {

	private List<GameAction> gameActions = new ArrayList<>();

	public List<GameAction> getGameHistory() {
		return new ArrayList<>(gameActions);
	}

	public void addAction(GameAction gameAction){
		gameActions.add(gameAction);
	}

}
