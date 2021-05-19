package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.marketrepresentation.ServerMarketRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameHistoryRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameHistoryImp implements GameHistory {

	private List<GameAction> gameActions = new ArrayList<>();

	@Override
	public List<GameAction> getGameHistory() {
		return new ArrayList<>(gameActions);
	}

	@Override
	public void addAction(GameAction gameAction){
		gameActions.add(gameAction);
	}

	@Override
	public ServerGameHistoryRepresentation getServerRepresentation() {
		return new ServerGameHistoryRepresentation(
			gameActions.stream().map(Representable::getServerRepresentation).collect(Collectors.toList())
		);
	}

	@Override
	public ServerGameHistoryRepresentation getServerRepresentationForPlayer(Player player) {
		return getServerRepresentation();
	}
}
