package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.controller.clientrequest.*;
import it.polimi.ingsw.server.controller.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.server.controller.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class GameState<I extends ServerMessage, F extends ServerMessage> {

	protected final GameManager gameManager;

	/**
	 * GameState constructor
	 * @param gameManager GameManager, see {@link GameManager}
	 */
	protected GameState(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	/**
	 * The initial messages that should be sent to the clients when this GameState becomes the currentGameState
	 * @return the default implementation returns an empty map (no message will be sent)
	 */
	public Map<Player, I> getInitialServerMessage() {
		return new HashMap<>();
	}

	/**
	 * The final messages that should be sent to the clients before changing to a new GameState
	 * @return the default implementation returns an empty map (no message will be sent)
	 */
	public Map<Player, F> getFinalServerMessage() {
		return new HashMap<>();
	}

	/**
	 * Method that verifies that the current state is closed
	 */
	public abstract boolean isStateDone();

	/**
	 * Method that changes the state of the game
	 */
	public abstract GameState getNextState();

	/**
	 * The method checks that the player's requests are valid. Specifically, it verifies that:
	 * - The player has not already sent a request.
	 * - The number of resources chosen by the player is the same as the one assigned to him.
	 * - The player only wants to add resources in valid storages (only on the shelves).
	 * - The resources chosen by the player do not violate the rules of the storage they are to be added to.
	 * - The number of leader cards chosen by the player is what is expected
	 * (players can only hold a certain number of cards).
	 * - The leader cards chosen by the player are part of the group of leader cards assigned to him.
	 * After verifying these requirements, the method assigns the chosen leader cards to each player,
	 * stores the resources in the shelves chosen by the player
	 * and moves the player in the Faith Track for a specific number of steps forward.
	 * Finally, the method returns these changes to each player, taking care to filter out private information:
	 * each player can only see his own leader cards, which he must keep secret from the other players.
	 * @param request specifying each player's choices, see {@link InitialChoicesClientRequest}
	 * @return a map specifying the message to be sent to each player
	 * @throws ResourceStorageRuleViolationException if at least one of the above requirements is not met:
	 * if a player's choice is not valid, the method throws an exception.
	 */
	public Map<Player, ServerMessage> handleInitialChoiceCR(InitialChoicesClientRequest request)
		throws ResourceStorageRuleViolationException {
		return null;
	}

	/**
	 * Method that discards the leader card chosen by the player according to his request.
	 * The leader card state changes from active to discarded.
	 * @param request request of the player to discard the leader card, see {@link DiscardLeaderCardClientRequest}
	 * @return messages sent to each player containing all changes made since the last game state update
	 * @throws LeaderCardRequirementsNotSatisfiedException if a player wants to discard some leader cards but not
	 * all card requirements have been satisfied
	 */
	public Map<Player, ServerMessage> handleRequestDiscardLeaderAction(DiscardLeaderCardClientRequest request)
		throws LeaderCardRequirementsNotSatisfiedException {
		return null;
	}

	/**
	 * Method that activates the leader card chosen by the player according to his request.
	 * The leader card state changes from hidden to active.
	 * @param request request of the player to activate the leader card, see {@link ActivateLeaderCardClientRequest}
	 * @return messages sent to each player containing all changes made since the last game state update
	 * @throws LeaderCardRequirementsNotSatisfiedException if a player wants to activate some leader cards but not
	 * all card requirements have been satisfied
	 */
	public Map<Player, GameUpdateServerMessage> handleRequestActivateLeaderAction(ActivateLeaderCardClientRequest request)
		throws LeaderCardRequirementsNotSatisfiedException {
		return null;
	}

	/**
	 * Method to perform the market action. The player selects a column of the market structure in order to get
	 * the marbles inside that column.
	 * Marbles can provide the player with:
	 * - Resources that the method places in the temporary storage
	 * - Faith points that let the player move on the faith path
	 * - Star resources provided by the special marbles
	 * @param request request made by the player to select a column of the market structure,
	 * see {@link MarketActionFetchColumnClientRequest}
	 * @return messages sent to each player containing all changes made since the last game state update
	 * @throws ResourceStorageRuleViolationException if a player wants to add some resources to a storage
	 * by violating a specific rule that the storage implements
	 * @throws NotEnoughResourcesException if a player wants to remove from a storage more resources than there are currently
	 */
	public Map<Player, ServerMessage> handleRequestFetchColumnMarketAction(MarketActionFetchColumnClientRequest request)
		throws ResourceStorageRuleViolationException, NotEnoughResourcesException {
		return null;
	}

	/**
	 * Method to perform the market action. The player selects a row of the market structure in order to get
	 * the marbles inside that row.
	 * Marbles can provide the player with:
	 * - Resources that the method places in the temporary storage
	 * - Faith points that let the player move on the faith path
	 * - Star resources provided by the special marbles
	 * @param request request made by the player to select a row of the market structure,
	 * see {@link MarketActionFetchRowClientRequest}
	 * @return messages sent to each player containing all changes made since the last game state update.
	 * @throws ResourceStorageRuleViolationException if a player wants to add some resources to a storage
	 * by violating a specific rule that the storage implements
	 * @throws NotEnoughResourcesException if a player wants to remove from a storage more resources than there are currently
	 */
	public Map<Player, ServerMessage> handleRequestFetchRowMarketAction(MarketActionFetchRowClientRequest request)
		throws ResourceStorageRuleViolationException, NotEnoughResourcesException {
		return null;
	}

	/**
	 * Method that allows the player to choose where to place the resources obtained from the market
	 * (including special resources) in his storages.
	 * @param request of the player to place the obtained resources in his storages,
	 * see {@link ManageResourcesFromMarketClientRequest}
	 * @return messages sent to each player containing all changes made since the last game state update
	 * @throws ResourceStorageRuleViolationException if a player wants to add some resources to a storage
	 * by violating a specific rule that the storage implements
	 */
	public Map<Player, ServerMessage> handleRequestManageResourcesFromMarket(ManageResourcesFromMarketClientRequest request)
		throws ResourceStorageRuleViolationException {
		return null;
	}

	public Map<Player, ServerMessage> handleRequestCustom(CustomClientRequest request) {
		return null;
	}

	/**
	 * Method that allows to buy the development card chosen by the player among the available ones.
	 * The card is paid by the player with the required cost resources.
	 * @param request request of the player to buy the development card, see {@link DevelopmentActionClientRequest}
	 * @return messages sent to each player containing all changes made since the last game state update
	 * @throws ForbiddenPushOnTopException if the rules imposed by the deck do not allow the development card
	 * to be pushed on the top of this Deck
	 */
	public Map<Player, ServerMessage> handleRequestDevelopmentAction(
		DevelopmentActionClientRequest request
	) throws ForbiddenPushOnTopException, NotEnoughResourcesException {
		return null;
	}

	/**
	 * Method that allows to activate productions. The player pays for the resources needed to be able
	 * to activate productions (the resources are first taken from special storages and shelves and then,
	 * if necessary, from the infinite chest). Then the player gets the resources as rewards, which are placed
	 * in the infinite chest, and the faith points which the respective productions provide.
	 * @param request request of the player to activate productions, see {@link ProductionActionClientRequest}
	 * @return messages sent to each player containing all changes made since the last game state update
	 * @throws ResourceStorageRuleViolationException if a player wants to add some resources to a storage
	 * by violating a specific rule that the storage implements
	 * @throws NotEnoughResourcesException if a player wants to remove from a storage more resources than there are currently
	 */
	public Map<Player, ServerMessage> handleRequestProductionAction(ProductionActionClientRequest request)
		throws ResourceStorageRuleViolationException, NotEnoughResourcesException {
		return null;
	}

	/**
	 * Method that ends the active player's turn.
	 * @param request request of the player to end his turn, see {@link EndTurnClientRequest}
	 * @return messages sent to each player containing all changes made since the last game state update
	 */
	public Map<Player, ServerMessage> handleRequestEndTurn(EndTurnClientRequest request) {
		return null;
	}

	protected Map<Player, GameUpdateServerMessage> buildGameUpdateServerMessage() {
		Set<ServerGameUpdate> gameUpdates = gameManager.getAllGameUpdates();
		Map<Player, GameUpdateServerMessage> serverMessages = new HashMap<>();
		for (Player player : gameManager.getPlayers())
			serverMessages.put(player, new GameUpdateServerMessage(gameUpdates));
		return serverMessages;
	}

	protected static Map<Player, ServerMessage> createInvalidRequestServerMessage(
		Player requestSender,
		String errorMessage,
		Object... messageArgs
	) {
		InvalidRequestServerMessage serverMessage = new InvalidRequestServerMessage(
			String.format(errorMessage, messageArgs)
		);
		return Map.of(requestSender, serverMessage);
	}

	protected static Map<Player, ServerMessage> createInvalidRequestSenderIsNotActivePlayer(
		Player sender,
		Player activePlayer
	) {
		return createInvalidRequestServerMessage(
			sender,
			"Player %s is not the active player. [Current active player is %s]",
			sender,
			activePlayer
		);
	}

}
