package it.polimi.ingsw.server.model.gamehistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.server.gameactionshistory.GameAction;

import java.util.ArrayList;
import java.util.List;

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
