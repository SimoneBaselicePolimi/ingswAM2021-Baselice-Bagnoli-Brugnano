package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.clientrequest.ManageResourcesFromMarketClientRequest;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.gamehistory.MainTurnInitialAction;
import it.polimi.ingsw.server.model.gamehistory.ManageResourcesFromMarketFinalAction;
import it.polimi.ingsw.server.model.gamehistory.ManageResourcesFromMarketInitialAction;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManageResourcesFromMarketState extends GameState {

	private final Player activePlayer;
	private boolean isMarketActionDone = false;

    public ManageResourcesFromMarketState(GameManager gameManager) {
    	super(gameManager);
		activePlayer = gameManager.getGameContext().getActivePlayer();
    }

    public Map<Player, ServerMessage> getInitialServerMessage() {
		gameManager.getGameHistory().addAction(
			new ManageResourcesFromMarketInitialAction(activePlayer)
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
			new ManageResourcesFromMarketFinalAction(activePlayer)
		);
		//empty serverMessage
		return gameManager.getPlayers().stream()
			.collect(
				Collectors.toMap(Function.identity(),
					player ->  new ServerMessage()
				));
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

		isMarketActionDone = true;
		return buildGameUpdateServerMessage();
	}

}
