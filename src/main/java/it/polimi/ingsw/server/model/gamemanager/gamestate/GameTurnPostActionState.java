package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.network.clientrequest.ActivateLeaderCardClientRequest;
import it.polimi.ingsw.network.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.clientrequest.DiscardLeaderCardClientRequest;
import it.polimi.ingsw.network.clientrequest.EndTurnClientRequest;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.network.servermessage.EndTurnServerMessage;
import it.polimi.ingsw.server.model.gamehistory.MainTurnFinalAction;
import it.polimi.ingsw.server.model.gamehistory.MainTurnInitialAction;
import it.polimi.ingsw.server.model.gamehistory.PostTurnFinalAction;
import it.polimi.ingsw.server.model.gamehistory.PostTurnInitialAction;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameTurnPostActionState extends GameState {

	private boolean isTurnOver = false;
	private final Player activePlayer;

    public GameTurnPostActionState(GameManager gameManager) {
        super(gameManager);
		activePlayer = gameManager.getGameContext().getActivePlayer();
    }

    public void GameTurnPostActionState(GameContext gameContext, Player player) {
	}

	public Map<Player, ServerMessage> getInitialServerMessage() {
		gameManager.getGameHistory().addAction(
			new PostTurnInitialAction(activePlayer)
		);
		//empty serverMessage
		return gameManager.getPlayers().stream()
			.collect(
				Collectors.toMap(Function.identity(),
					player ->  new ServerMessage()
				));
	}

	public Map<Player,ServerMessage> getFinalServerMessage() {
		gameManager.getGameHistory().addAction(
			new PostTurnFinalAction(activePlayer)
		);
		//empty serverMessage
		return gameManager.getPlayers().stream()
			.collect(
				Collectors.toMap(Function.identity(),
					player ->  new ServerMessage()
				));
	}

	public boolean isStateDone() {
		return isTurnOver;
	}

	public GameState getNextState() {
		return new GameTurnMainActionState(gameManager);
	}

	@Override
	public Map<Player, ServerMessage> handleRequestLeaderAction(DiscardLeaderCardClientRequest request) throws LeaderCardRequirementsNotSatisfiedException {

    	// discard leader cards
		for (LeaderCard leaderCard : request.leaderCardsThePlayerWantsToDiscard)
			leaderCard.discardLeaderCard();

		return buildGameUpdateServerMessage();
	}

	@Override
	public Map<Player, GameUpdateServerMessage> handleRequestLeaderAction(ActivateLeaderCardClientRequest request) throws LeaderCardRequirementsNotSatisfiedException {

		// activate leader cards
		for (LeaderCard leaderCard : request.leaderCardsThePlayerWantsToActivate)
			leaderCard.activateLeaderCard(gameManager.getGameContext().getPlayerContext(activePlayer));

		return buildGameUpdateServerMessage();
	}


	public Map<Player, EndTurnServerMessage> handleRequestEndTurn(EndTurnClientRequest request) {
    	isTurnOver = true;
		return gameManager.getPlayers().stream()
			.collect(
				Collectors.toMap(Function.identity(),
					player ->  new EndTurnServerMessage()
				));
	}

}
