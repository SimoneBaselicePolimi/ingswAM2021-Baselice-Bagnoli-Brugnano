package it.polimi.ingsw.server.model.gamecontext;

import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.market.MarketImp;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTableImp;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContextImp;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathImp;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.ServerGameContextRepresentation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The game context aggregates all the information relative to the current game state.
 * The main components are:
 * <ul>
 *     <li> An ordered list of players that specifies the player turns order (in the first turn of the game the first
 *     player in the list will play, then the second player in the list will play his turn, then the third, etc.)</li>
 *     <li> A {@link PlayerContextImp} object for each player. The PlayerContext aggregates all the information relative to
 *     a specific player </li>
 *     <li> The market, see {@link MarketImp}).</li>
 *     <li> The development cards table, see {@link DevelopmentCardsTableImp} </li>
 *     <li> The faith path, see {@link FaithPathImp} </li>
 * </ul>
 * <p>
 * The game context also offers some utility methods that aggregate together information obtained by multiple
 * subcomponents. For example the method {@link GameContextImp#getDevelopmentCardsPlayerCanBuy(Player)} will return the
 * development cards from the DevelopmentCardsTable that the player has enough resources to buy (also considering the
 * possible discounts that may apply, see {@link it.polimi.ingsw.server.model.gameitems.DevelopmentCardCostDiscount})
 */
public class GameContextImp implements GameContext {

	private Market market;
	private DevelopmentCardsTable developmentCardsTable;
	private FaithPath faithPath;
	private List<Player> playersOrder;
	private Map<Player, PlayerContext> playerContexts;
	private Player activePlayer;

	/**
	 * Creates the game context associated with this game.
	 * <p>
	 * Note1: there should only be one gameContext for each game (in the scope of a specific game, the game context is
	 * like a singleton).
	 * Note2: this constructor is marked as protected because this class should only ever be initialized by the
	 *  {@link it.polimi.ingsw.server.model.gamecontext.GameContextBuilder} and thus the constructor should never be
	 *  called from outside this package
	 * @param market Market, see {@link MarketImp}
	 * @param developmentCardsTable the table with the development cards the players can buy, see
	 * 	{@link DevelopmentCardsTableImp}
	 * @param faithPath the faith path, see {@link FaithPathImp}
	 * @param playersOrder An ordered list of players that specifies the player turns order
	 * @param playerContexts a map that associates every player with his player context. See {@link PlayerContextImp}
	 */
	protected GameContextImp(
			Market market,
			DevelopmentCardsTable developmentCardsTable,
			FaithPath faithPath,
			List<Player> playersOrder,
			Map<Player, PlayerContext> playerContexts
	) {
	    if(playersOrder.size() != playerContexts.keySet().size() ||
				playerContexts.keySet().stream().anyMatch(p -> !playersOrder.contains(p)))
	    	throw new IllegalArgumentException(
	    			"The number of players in playersOrder should be equal to the number of keys in playerContexts"
			);
	    this.market = market;
	    this.developmentCardsTable = developmentCardsTable;
	    this.faithPath = faithPath;
	    this.playersOrder = new ArrayList<>(playersOrder);
	    this.playerContexts = new HashMap<>(playerContexts);
	    this.activePlayer = playersOrder.get(0);
	}

	/**
	 * An ordered list of players that specifies the player turns order (in the first turn of the game the first
	 *  player in the list will play, then the second player in the list will play his turn, then the third, etc.)
	 * @return the ordered list
	 */
	@Override
	public List<Player> getPlayersTurnOrder() {
		return new ArrayList<>(playersOrder);
	}

	/**
	 * This method returns the development cards from the DevelopmentCardsTable that the player has enough resources to
	 * buy (also considering the possible discounts that may apply, see
	 * {@link it.polimi.ingsw.server.model.gameitems.DevelopmentCardCostDiscount})
	 * @param player the player, see {@link Player}
	 * @return a set of DevelopmentCards the specified player can buy
	 */
	@Override
	public Set<DevelopmentCard> getDevelopmentCardsPlayerCanBuy(Player player) {
		return developmentCardsTable.getAvailableCards().stream()
				.filter(card -> ResourceUtils.areResourcesAContainedInB(
						card.getPurchaseCost(),
						getPlayerContext(player).getAllResources())
				).collect(Collectors.toSet());
	}

	/**
	 * Returns all the game information for a specific player
	 * @param player the player, see {@link Player}
	 * @return returns the playerContext for the specified player, see {@link PlayerContextImp}
	 */
	@Override
	public PlayerContext getPlayerContext(Player player) {
		return playerContexts.get(player);
	}

	/**
	 * @return returns the Market, see {@link MarketImp}
	 */
	@Override
	public Market getMarket() {
		return market;
	}

	/**
	 * Returns the DevelopmentCardTable, a table with the development cards the players can buy
	 * @return returns the DevelopmentCardTable, see {@link DevelopmentCardsTableImp}
	 */
	@Override
	public DevelopmentCardsTable getDevelopmentCardsTable() {
		return developmentCardsTable;
	}

	/**
	 * @return returns the FaithPath, see {@link FaithPathImp}
	 */
	@Override
	public FaithPath getFaithPath() {
		return faithPath;
	}

	@Override
	public Player getActivePlayer() {
		return activePlayer;
	}

	//TODO add JavaDoc e Test
	@Override
	public Player startNextPlayerTurn(){
		activePlayer = getPlayersTurnOrder().get(
			(getPlayersTurnOrder().indexOf(activePlayer) + 1) % getPlayersTurnOrder().size()
		);
		return activePlayer;
	}

	@Override
	public ServerGameContextRepresentation getServerRepresentation() {
		return new ServerGameContextRepresentation(
			market.getServerRepresentation(),
			developmentCardsTable.getServerRepresentation(),
			faithPath.getServerRepresentation(),
			new ArrayList<Player>(playersOrder),
			playerContexts.entrySet().stream()
				.collect((Collectors.toMap(
					Map.Entry::getKey,
					e -> e.getValue().getServerRepresentation()
				))),
			activePlayer
		);
	}

	@Override
	public ServerGameContextRepresentation getServerRepresentationForPlayer(Player player) {
		return getServerRepresentation();
	}

	@Override
	public Set<DevelopmentCard> getPurchasableDevelopmentCardsForPlayer(Player player) {
		return developmentCardsTable.getAvailableCards().stream()
			.filter(c -> {

				PlayerContext playerContext = getPlayerContext(player);

				boolean canAddCardInAtLeastOneDeck = playerContext.getPlayerDevCardsDecks().stream().anyMatch(d ->
						playerContext.canAddDevelopmentCard(c, playerContext.getPlayerDevCardsDecks().indexOf(d))
				);

				Map<ResourceType, Integer> costWithDiscount = ResourceUtils.relativeDifference(
					c.getPurchaseCost(),
					playerContext.getActiveLeaderCardsDiscounts()
				).entrySet().stream()
					.filter(e -> e.getValue() > 0)
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
				boolean hasEnoughResources = ResourceUtils.areResourcesAContainedInB(costWithDiscount, playerContext.getAllResources());

				return canAddCardInAtLeastOneDeck && hasEnoughResources;

			}).collect(Collectors.toSet());
	}

}
