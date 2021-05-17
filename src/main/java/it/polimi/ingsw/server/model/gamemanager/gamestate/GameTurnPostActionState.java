package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.clientrequest.EndTurnClientRequest;
import it.polimi.ingsw.network.servermessage.EndTurnServerMessage;
import it.polimi.ingsw.server.model.gamehistory.PostTurnFinalAction;
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
public class GameTurnPostActionState extends LeaderCardActionState {

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
	 * Method that ends the active player's turn.
	 * @param request request of the player to end his turn, see {@link EndTurnClientRequest}
	 * @return messages sent to each player containing all changes made since the last game state update
	 */
	public Map<Player, ServerMessage> handleRequestEndTurn(EndTurnClientRequest request) {

		if(!request.player.equals(activePlayer))
			return createInvalidRequestSenderIsNotActivePlayer(request.player, activePlayer);

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
