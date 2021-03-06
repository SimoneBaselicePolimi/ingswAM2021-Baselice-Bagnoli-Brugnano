package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.gameactionshistory.DiscardedResourcesMarketAction;
import it.polimi.ingsw.server.gameactionshistory.ObtainedResourcesMarketAction;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.controller.clientrequest.ManageResourcesFromMarketClientRequest;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class represents the phase of the game where the player has already performed a market action and
 * the resources obtained from the marbles have been put into the temporary storage.
 * In this state, the player must choose where to place the resources obtained in his storages and,
 * if necessary, which type of resources he wants to get as star resources and where he wants to place them
 * (the player knows how many special resources he has obtained as a result of the market action).
 * This state of the game ends when the player has performed the previously described action and the game switches
 * to the GameTurnPostActionState.
 */
public class ManageResourcesFromMarketState extends GameState {

	private final Player activePlayer;
	private boolean isMarketActionDone = false;

	/**
	 * ManageResourcesFromMarketState constructor
	 * @param gameManager GameManager, see {@link GameManager}
	 */
    public ManageResourcesFromMarketState(GameManager gameManager) {
    	super(gameManager);
		activePlayer = gameManager.getGameContext().getActivePlayer();
    }

	/**
	 * Method that verifies that the current state is closed
	 * @return true if the player has placed the resources obtained from the market in his storages
	 */
	public boolean isStateDone() {
		return isMarketActionDone;
	}

	/**
	 * Method that changes the state of the game.
	 * The player has placed the resources obtained from the market in his storages and the game switches to the
	 * GameTurnPostActionState.
	 * @return GameTurnPostActionState, see {@link GameTurnPostActionState}
	 */
	public GameTurnPostActionState getNextState() {
		return new GameTurnPostActionState(gameManager);
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
	public Map<Player,ServerMessage> handleRequestManageResourcesFromMarket(
		ManageResourcesFromMarketClientRequest request
	) throws ResourceStorageRuleViolationException, NotEnoughResourcesException {

		if(!request.player.equals(activePlayer))
			return createInvalidRequestSenderIsNotActivePlayer(request.player, activePlayer);

		for (ResourceStorage storage : request.resourcesInModifiedStorages.keySet()) {
			storage.removeResources(storage.peekResources());
		}

		for (ResourceStorage storage : request.resourcesInModifiedStorages.keySet()) {
			storage.addResources(request.resourcesInModifiedStorages.get(storage));
		}

		gameManager.getGameHistory().addAction(
			new ObtainedResourcesMarketAction(
				activePlayer,
				ResourceUtils.sum(request.resourcesInModifiedStorages.values())
			)
		);

		int numberOfResourcesLeftInTemporaryStorage = request.resourcesLeftInTemporaryStorage.values().stream()
			.mapToInt(i -> i)
			.sum();
		if (numberOfResourcesLeftInTemporaryStorage != 0){
			Set<Player> otherPlayers = gameManager.getPlayers().stream()
				.filter(p -> !p.equals(activePlayer))
				.collect(Collectors.toSet());

			for (Player otherPlayer : otherPlayers) {
				gameManager.getGameContext().getFaithPath().move(
					otherPlayer,
					numberOfResourcesLeftInTemporaryStorage
				);
			}
			if(gameManager.singlePlayerMode())
				gameManager.getSinglePlayerGameContext().get().getFaithPathSinglePlayer().moveBlackCross(numberOfResourcesLeftInTemporaryStorage);

			gameManager.getGameHistory().addAction(
				new DiscardedResourcesMarketAction(
					activePlayer,
					numberOfResourcesLeftInTemporaryStorage
				)
			);
		}

		isMarketActionDone = true;
		return buildGameUpdateServerMessage();
	}

}
