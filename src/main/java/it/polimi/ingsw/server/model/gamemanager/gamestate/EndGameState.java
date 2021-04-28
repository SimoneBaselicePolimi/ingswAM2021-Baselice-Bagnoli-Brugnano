package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.network.clientrequest.ActivateLeaderCardClientRequest;
import it.polimi.ingsw.network.clientrequest.DiscardLeaderCardClientRequest;
import it.polimi.ingsw.network.clientrequest.EndTurnClientRequest;
import it.polimi.ingsw.network.servermessage.*;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamehistory.PostTurnFinalAction;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;

import java.util.Map;
import java.util.Set;
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
