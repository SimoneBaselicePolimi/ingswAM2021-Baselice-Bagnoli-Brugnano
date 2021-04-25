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
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class represents the main phase of the game.
 * During his turn, the player can perform one of the three possible actions of the game:
 * - Take resources from the market
 * - Buy a development card
 * - Activate productions
 * In addition, during his turn the player can also perform leader actions to discard or activate his leader cards.
 * This action can be performed as many times as the player wishes to do so (as long as it is possible to do so)
 * either before or after performing one of the three main actions mentioned above.
 */
public class GameTurnMainActionState extends GameState {

	//true after LeaderAction
	private boolean mainActionDone = false;
	private final Player activePlayer;

	/**
	 * GameTurnMainActionState constructor
	 * @param gameManager GameManager, see {@link GameManager}
	 */
	public GameTurnMainActionState(GameManager gameManager) {
		super(gameManager);
		activePlayer = gameManager.getGameContext().getActivePlayer();
    }

	/**
	 * Method that sends to each player the initial message at the start of each turn.
	 * @return a map specifying the initial message to be sent to each player
	 */
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

	/**
	 * Method that sends to each player the final message at the end of each turn.
	 * @return a map specifying the final message to be sent to each player
	 */
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

	/**
	 * Method that verifies that the current status is closed
	 * @return true if the player's turn is over
	 */
	public boolean isStateDone() {
		return mainActionDone;
	}

	//TODO
	public GameState getNextState() { return new GameTurnPostActionState(gameManager); }


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
	) throws NotEnoughResourcesException, ResourceStorageRuleViolationException {

		PlayerContext playerContext = gameManager.getGameContext().getPlayerContext(request.player);

		//remove resource costs
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

		//add resource rewards
		Set<Map<ResourceType, Integer>> productionRewards = request.productions.stream()
			.map(Production:: getProductionResourceReward).collect(Collectors.toSet());

		Map<ResourceType, Integer> productionRewardsWithStarResources = ResourceUtils.sum(
			ResourceUtils.sum(productionRewards),
			request.starResourceReward
		);

		playerContext.getInfiniteChest().addResources(productionRewardsWithStarResources);

		mainActionDone = true;
		return buildGameUpdateServerMessage();
	}

}
