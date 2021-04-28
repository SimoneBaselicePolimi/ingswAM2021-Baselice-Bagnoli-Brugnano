package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.network.clientrequest.ActivateLeaderCardClientRequest;
import it.polimi.ingsw.network.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.clientrequest.DiscardLeaderCardClientRequest;
import it.polimi.ingsw.network.clientrequest.EndTurnClientRequest;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.network.servermessage.EndTurnServerMessage;
import it.polimi.ingsw.server.model.gamehistory.PostTurnFinalAction;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameTurnPostActionState extends GameState {

	private boolean isTurnOver = false;
	private final Player activePlayer;

    public GameTurnPostActionState(GameManager gameManager) {
        super(gameManager);
		activePlayer = gameManager.getGameContext().getActivePlayer();
    }

	public boolean isStateDone() {
		return isTurnOver;
	}

	public GameState getNextState() {
		return new GameTurnMainActionState(gameManager);
	}

	@Override
	public Map<Player, ServerMessage> handleRequestDiscardLeaderAction(DiscardLeaderCardClientRequest request) throws LeaderCardRequirementsNotSatisfiedException {

    	// discard leader cards
		for (LeaderCard leaderCard : request.leaderCardsThePlayerWantsToDiscard)
			leaderCard.discardLeaderCard();

		return buildGameUpdateServerMessage();
	}

	@Override
	public Map<Player, GameUpdateServerMessage> handleRequestActivateLeaderAction(ActivateLeaderCardClientRequest request) throws LeaderCardRequirementsNotSatisfiedException {

		// activate leader cards
		for (LeaderCard leaderCard : request.leaderCardsThePlayerWantsToActivate)
			leaderCard.activateLeaderCard(gameManager.getGameContext().getPlayerContext(activePlayer));

		return buildGameUpdateServerMessage();
	}


	public Map<Player, EndTurnServerMessage> handleRequestEndTurn(EndTurnClientRequest request) {
    	isTurnOver = true;
		gameManager.getGameHistory().addAction(
			new PostTurnFinalAction(activePlayer)
		);
		Set<GameUpdate> updates = gameManager.getAllGameUpdates();
		return gameManager.getPlayers().stream()
			.collect(
				Collectors.toMap(Function.identity(),
					player ->  new EndTurnServerMessage(updates)
				));
	}

}
