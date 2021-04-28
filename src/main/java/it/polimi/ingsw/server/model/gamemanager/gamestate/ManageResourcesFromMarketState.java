package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.clientrequest.ManageResourcesFromMarketClientRequest;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathEvent;
import it.polimi.ingsw.server.model.gamehistory.*;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ManageResourcesFromMarketState extends GameState {

	private final Player activePlayer;
	private boolean isMarketActionDone = false;

    public ManageResourcesFromMarketState(GameManager gameManager) {
    	super(gameManager);
		activePlayer = gameManager.getGameContext().getActivePlayer();
    }

	public boolean isStateDone() {
		return isMarketActionDone;
	}

	public GameState getNextState() {
		return new GameTurnPostActionState(gameManager);
	}

	public Map<Player,ServerMessage> handleRequestManageResourcesFromMarket(
		ManageResourcesFromMarketClientRequest request
	) throws ResourceStorageRuleViolationException {

		for (ResourceStorage storage : request.resourcesToAddByStorage.keySet())
			storage.addResources(request.resourcesToAddByStorage.get(storage));

		for (ResourceStorage storage : request.starResourcesChosenToAddByStorage.keySet())
			storage.addResources(request.starResourcesChosenToAddByStorage.get(storage));

		Map<ResourceType, Integer> totalResourcesObtained = ResourceUtils.sum(
			ResourceUtils.sum(request.resourcesToAddByStorage.values()),
			ResourceUtils.sum(request.starResourcesChosenToAddByStorage.values())
		);

		gameManager.getGameHistory().addAction(
			new ObtainedResourcesMarketAction(activePlayer, totalResourcesObtained));

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
