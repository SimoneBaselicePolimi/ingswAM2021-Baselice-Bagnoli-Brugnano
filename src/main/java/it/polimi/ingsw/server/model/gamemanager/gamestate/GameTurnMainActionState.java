package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.controller.clientrequest.DevelopmentActionClientRequest;
import it.polimi.ingsw.server.controller.clientrequest.MarketActionFetchColumnClientRequest;
import it.polimi.ingsw.server.controller.clientrequest.MarketActionFetchRowClientRequest;
import it.polimi.ingsw.server.controller.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.server.controller.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.gameactionshistory.DevelopmentAction;
import it.polimi.ingsw.server.gameactionshistory.MainTurnInitialAction;
import it.polimi.ingsw.server.gameactionshistory.ObtainedMarblesMarketAction;
import it.polimi.ingsw.server.gameactionshistory.ProductionAction;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * This class represents the main phase of the game.
 * During his turn, the player has to perform one of these three possible actions of the game:
 * - Take resources from the market
 * - Buy a development card
 * - Activate productions
 * In addition, during his turn the player can also perform leader actions to discard or activate his leader cards.
 * This action can be performed as many times as the player wishes to do, so (as long as it is possible to do so)
 * either before or after performing one of the three main actions mentioned above.
 */
public class GameTurnMainActionState extends LeaderCardActionState {

	private boolean mainActionDone = false;
	private final Player activePlayer;
	private boolean hasPlayerDoneMarketAction = false;
	Market market = gameManager.getGameContext().getMarket();
	private final PlayerContext playerContext;

	/**
	 * GameTurnMainActionState constructor
	 * @param gameManager GameManager, see {@link GameManager}
	 */
	public GameTurnMainActionState(GameManager gameManager) {
		super(gameManager);
		activePlayer = gameManager.getGameContext().getActivePlayer();
		playerContext = gameManager.getGameContext().getPlayerContext(activePlayer);
    }

	/**
	 * Method that sends to each player the initial message at the start of each turn.
	 * @return a map specifying the initial message to be sent to each player
	 */
	public Map<Player, GameUpdateServerMessage> getInitialServerMessage() {

		gameManager.getGameHistory().addAction(
			new MainTurnInitialAction(activePlayer)
		);

		return buildGameUpdateServerMessage();
	}

	/**
	 * Method that verifies that the current state is closed
	 * @return true if the player has performed one of the three main actions
	 */
	public boolean isStateDone() {
		return mainActionDone;
	}

	/**
	 * Method that changes the state of the game.
	 * The main state ends and the next possible game states are:
	 * - The post state (see {@link GameTurnPostActionState}) if the player has bought a development card
	 * or activated a production
	 * - The market state (see {@link ManageResourcesFromMarketState}) if the player has requested to take resources
	 * from the market
	 * @return GameState new game state, it can be a ManageResourcesFromMarketState or a GameTurnPostActionState,
	 * according to the action performed by the player in the current state
	 */
	public GameState getNextState() {
		if (hasPlayerDoneMarketAction)
			return new ManageResourcesFromMarketState(gameManager);
		else
			return new GameTurnPostActionState(gameManager);
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
	@Override
	public Map<Player, ServerMessage> handleRequestFetchColumnMarketAction(MarketActionFetchColumnClientRequest request)
		throws ResourceStorageRuleViolationException, NotEnoughResourcesException {

		if(!request.player.equals(activePlayer))
			return createInvalidRequestSenderIsNotActivePlayer(request.player, activePlayer);

		MarbleColour[] marblesThePlayerGets = market.fetchMarbleColumn(request.column);
		return doMarketAction(marblesThePlayerGets);
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
	 * @return messages sent to each player containing all changes made since the last game state update
	 * @throws ResourceStorageRuleViolationException if a player wants to add some resources to a storage
	 * by violating a specific rule that the storage implements
	 * @throws NotEnoughResourcesException if a player wants to remove from a storage more resources than there are currently
	 */
	public Map<Player, ServerMessage> handleRequestFetchRowMarketAction(MarketActionFetchRowClientRequest request)
		throws ResourceStorageRuleViolationException, NotEnoughResourcesException {

		if(!request.player.equals(activePlayer))
			return createInvalidRequestSenderIsNotActivePlayer(request.player, activePlayer);

		MarbleColour[] marblesThePlayerGets = market.fetchMarbleRow(request.row);
		return doMarketAction(marblesThePlayerGets);

	}

	/**
	 * Private method called by the methods that allow the player to perform the market action.
	 * @param marblesThePlayerGets marbles obtained from the market row or column selected by the player
	 * @return  messages sent to each player containing all changes made since the last game state update
	 * @throws ResourceStorageRuleViolationException if a player wants to add some resources to a storage
	 * by violating a specific rule that the storage implements
	 * @throws NotEnoughResourcesException if a player wants to remove from a storage more resources than there are currently
	 */
	private Map<Player, ServerMessage> doMarketAction (MarbleColour[] marblesThePlayerGets)
		throws ResourceStorageRuleViolationException, NotEnoughResourcesException {

		int numberOfStarResources = 0;
		int numberOfFaithPoints = 0;

		Map<ResourceType, Integer> resources = new HashMap<>();

		for (MarbleColour marbleColour : marblesThePlayerGets){

			if(marbleColour.getResourceType().isPresent())
				resources = ResourceUtils.sum(resources, Map.of(marbleColour.getResourceType().get(), 1));

			if (marbleColour.isSpecialMarble())
				numberOfStarResources += 1;

			numberOfFaithPoints += marbleColour.getFaithPoints();

		}

		gameManager.getGameContext().getFaithPath().move(activePlayer, numberOfFaithPoints);
		playerContext.setTemporaryStorageResources(resources);
		playerContext.setTempStarResources(numberOfStarResources);

		gameManager.getGameHistory().addAction(
			new ObtainedMarblesMarketAction(
				activePlayer,
				Arrays.stream(marblesThePlayerGets).collect(groupingBy(
					Function.identity(),
					Collectors.mapping(m -> 1, Collectors.reducing(0, Integer::sum))
				)))
		);

		hasPlayerDoneMarketAction = true;
		mainActionDone = true;
		return buildGameUpdateServerMessage();

	}

	/**
	 * Method that allows to buy the development card chosen by the player among the available ones.
	 * The card is paid by the player with the required cost resources.
	 * @param request request of the player to buy the development card, see {@link DevelopmentActionClientRequest}
	 * @return messages sent to each player containing all changes made since the last game state update
	 * @throws ForbiddenPushOnTopException if the rules imposed by the deck do not allow the development card
	 * to be pushed on the top of this Deck
	 */
	@Override
	public Map<Player, ServerMessage> handleRequestDevelopmentAction(
		DevelopmentActionClientRequest request
	) throws ForbiddenPushOnTopException, NotEnoughResourcesException {

		if(!request.player.equals(activePlayer))
			return createInvalidRequestSenderIsNotActivePlayer(request.player, activePlayer);

		//add development card to the player deck and remove that from the top of the table deck
		GameContext gameContext = gameManager.getGameContext();
		gameContext.getPlayerContext(request.player)
			.addDevelopmentCard(
				gameContext.getDevelopmentCardsTable().popCard(
					request.developmentCard.getLevel(),
					request.developmentCard.getColour()
				),
				request.deckNumber
			);

		//remove required cost resources from the shelves of the player, then the leader card storages and eventually
		// the infinite chest
		playerContext.removeResourcesBasedOnResourcesStoragesPriority(
		    ResourceUtils.relativeDifference(
				request.developmentCard.getPurchaseCost(),
				playerContext.getActiveLeaderCardsDiscounts()
			).entrySet().stream()
				.filter(e -> e.getValue() > 0)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
		);

		gameManager.getGameHistory().addAction(
			new DevelopmentAction(activePlayer, request.developmentCard)
		);

		mainActionDone = true;
		return buildGameUpdateServerMessage();
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
	@Override
	public Map<Player, ServerMessage> handleRequestProductionAction(ProductionActionClientRequest request)
		throws NotEnoughResourcesException, ResourceStorageRuleViolationException {

		if(!request.player.equals(activePlayer))
			return createInvalidRequestSenderIsNotActivePlayer(request.player, activePlayer);

		PlayerContext playerContext = gameManager.getGameContext().getPlayerContext(request.player);

		//remove resource costs
		Set<Map<ResourceType, Integer>> productionCosts = request.productions.stream()
			.map(Production::getProductionResourceCost).collect(Collectors.toSet());

		Map<ResourceType, Integer> totalResourceToRemove = ResourceUtils.sum(
			ResourceUtils.sum(productionCosts),
			request.starResourceCost
		);

		playerContext.removeResourcesBasedOnResourcesStoragesPriority(totalResourceToRemove);

		//add resource rewards
		Set<Map<ResourceType, Integer>> productionRewards = request.productions.stream()
			.map(Production:: getProductionResourceReward).collect(Collectors.toSet());

		Map<ResourceType, Integer> productionRewardsWithStarResources = ResourceUtils.sum(
			ResourceUtils.sum(productionRewards),
			request.starResourceReward
		);

		playerContext.getInfiniteChest().addResources(productionRewardsWithStarResources);

		//add faith points
		gameManager.getGameContext().getFaithPath().move(
			activePlayer,
			request.productions.stream().mapToInt(Production::getProductionFaithReward).sum()
		);

		gameManager.getGameHistory().addAction(
			new ProductionAction(activePlayer, request.productions)
		);

		mainActionDone = true;
		return buildGameUpdateServerMessage();
	}

}
