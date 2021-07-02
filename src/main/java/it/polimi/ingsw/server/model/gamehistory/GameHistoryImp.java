package it.polimi.ingsw.server.model.gamehistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.server.gameactionshistory.GameAction;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the set of most significant actions that take place during the game.
 * This list of actions is useful as it allows any player participating in the game to know the status of the game.
 */
public class GameHistoryImp implements GameHistory {
	private List<GameAction> gameActions = new ArrayList<>();

	@JsonIgnore
	@Override
	public List<GameAction> getGameHistory() {
		return new ArrayList<>(gameActions);
	}

	@Override
	public void addAction(GameAction gameAction){
		gameActions.add(gameAction);
	}

}
