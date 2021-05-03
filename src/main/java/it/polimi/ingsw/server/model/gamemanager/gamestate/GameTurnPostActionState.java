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

/**
 * This class represents the last phase of the player's turn.
 * The player has already performed one of the three main actions.
 * In this turn, the active player can choose to perform leader actions to discard or activate his leader cards
 * and he can choose to end his turn.
 */
public class GameTurnPostActionState extends GameState {

	private boolean isTurnOver = false;
	private final Player activePlayer;

	/**
	 * GameTurnPostActionState constructor
	 * @param gameManager GameManager, see {@link GameManager}
	 */
    public GameTurnPostActionState(GameManager gameManager) {
        super(gameManager);
		activePlayer = gameManager.getGameContext().getActivePlayer();
    }

	/**
	 * Method that verifies that the current state is closed
	 * @return true if the player's turn is over
	 */
	public boolean isStateDone() {
		return isTurnOver;
	}

	/**
	 * Method that changes the state of the game.
	 * The current state ends and the player's turn is over.
	 * The game switches to the main state where the new player can choose what action to perform.
	 * @return GameTurnMainActionState main state of the game, see {@link GameTurnMainActionState}
	 */
	public GameTurnMainActionState getNextState() {
		return new GameTurnMainActionState(gameManager);
	}

	/**
	 * Method that discards the leader card chosen by the player according to his request.
	 * The leader card state changes from active to discard.
	 * @param request request of the player to discard the leader card, see {@link DiscardLeaderCardClientRequest}
	 * @return messages sent to each player containing all changes made since the last game state update
	 * @throws LeaderCardRequirementsNotSatisfiedException if a player wants to discard some leader cards but not
	 * all card requirements have been satisfied
	 */
	@Override
	public Map<Player, ServerMessage> handleRequestDiscardLeaderAction(DiscardLeaderCardClientRequest request) throws LeaderCardRequirementsNotSatisfiedException {

    	// discard leader cards
		for (LeaderCard leaderCard : request.leaderCardsThePlayerWantsToDiscard)
			leaderCard.discardLeaderCard();

		return buildGameUpdateServerMessage();
	}

	/**
	 * Method that activates the leader card chosen by the player according to his request.
	 * The leader card state changes from hidden to active.
	 * @param request request of the player to activate the leader card, see {@link ActivateLeaderCardClientRequest}
	 * @return messages sent to each player containing all changes made since the last game state update
	 * @throws LeaderCardRequirementsNotSatisfiedException if a player wants to activate some leader cards but not
	 * all card requirements have been satisfied
	 */
	@Override
	public Map<Player, GameUpdateServerMessage> handleRequestActivateLeaderAction(ActivateLeaderCardClientRequest request) throws LeaderCardRequirementsNotSatisfiedException {

		// activate leader cards
		for (LeaderCard leaderCard : request.leaderCardsThePlayerWantsToActivate)
			leaderCard.activateLeaderCard(gameManager.getGameContext().getPlayerContext(activePlayer));

		return buildGameUpdateServerMessage();
	}

	/**
	 * Method that ends the active player's turn.
	 * @param request request of the player to end his turn, see {@link EndTurnClientRequest}
	 * @return messages sent to each player containing all changes made since the last game state update
	 */
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
