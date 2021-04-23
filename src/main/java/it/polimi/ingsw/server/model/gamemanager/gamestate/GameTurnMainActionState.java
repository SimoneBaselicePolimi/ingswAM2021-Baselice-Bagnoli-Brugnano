package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.network.clientrequest.*;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.servermessage.*;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gamehistory.MainTurnFinalAction;
import it.polimi.ingsw.server.model.gamehistory.MainTurnInitialAction;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameTurnMainActionState extends GameState {

	//true after LeaderAction
	private boolean mainActionDone = false;
	private final Player activePlayer;

	public GameTurnMainActionState(GameManager gameManager) {
		super(gameManager);
		activePlayer = gameManager.getGameContext().getActivePlayer();
    }

	public Map<Player, ServerMessage> getInitialServerMessage() {
		gameManager.getGameHistory().addAction(
			new MainTurnInitialAction(activePlayer)
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
			new MainTurnFinalAction(activePlayer)
		);
		//empty serverMessage
		return gameManager.getPlayers().stream()
			.collect(
				Collectors.toMap(Function.identity(),
					player ->  new ServerMessage()
				));
	}

	public boolean isStateDone() {
		return mainActionDone;
	}

	public GameState getNextState() {
		return new GameTurnPostActionState(gameManager);
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

		@Override
	public Map<Player, ServerMessage> handleRequestFetchColumnMarketAction(MarketActionFetchColumnClientRequest request) {
		return null;
	}

	public Map<Player, ServerMessage> handleRequestFetchRowMarketAction(MarketActionFetchRowClientRequest request) {
		return null;
	}

	@Override
	public Map<Player, ServerMessage> handleRequestDevelopmentAction(DevelopmentActionClientRequest request) throws ForbiddenPushOnTopException {

		//add development card to the player deck and remove that from the top of the table deck
		GameContext gameContext = gameManager.getGameContext();
		gameContext.getPlayerContext(request.player)
			.addDevelopmentCard(
				gameContext.getDevelopmentCardsTable().popCard
					(
						request.developmentCard.getLevel(),
						request.developmentCard.getColour()
					),
					request.deckNumber
			);

		mainActionDone = true;
		return buildGameUpdateServerMessage();
	}

	@Override
	public Map<Player, ServerMessage> handleRequestProductionAction(
		ProductionActionClientRequest request
	) throws NotEnoughResourcesException {

		PlayerContext playerContext = gameManager.getGameContext().getPlayerContext(request.player);

		Set<Map<ResourceType, Integer>> productionCosts = request.productions.stream()
			.map(Production::getProductionResourceCost).collect(Collectors.toSet());

		Map<ResourceType, Integer> resourceCostLeftToRemove = ResourceUtils.sum(
			ResourceUtils.sum(productionCosts),
			request.starResourceCost
		);

		for (ResourceStorage storage : playerContext.getResourceStoragesForResourcesFromMarket()) {
			Map<ResourceType, Integer> resourcesRemovableFromStorage = ResourceUtils.intersection(
				storage.peekResources(),
				resourceCostLeftToRemove
			);
			storage.removeResources(resourcesRemovableFromStorage);
			resourceCostLeftToRemove = ResourceUtils.difference(
				resourceCostLeftToRemove,
				resourcesRemovableFromStorage
			);
		}

		playerContext.getInfiniteChest().removeResources(resourceCostLeftToRemove);

		mainActionDone = true;
		return buildGameUpdateServerMessage();
	}

}
