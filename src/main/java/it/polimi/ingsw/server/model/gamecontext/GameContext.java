package it.polimi.ingsw.server.model.gamecontext;

import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The game context aggregates all the information relative to the current game state.
 * The main components are:
 * <ul>
 *     <li> An ordered list of players that specifies the player turns order (in the first turn of the game the first
 *     player in the list will play, then the second player in the list will play his turn, then the third, etc.)</li>
 *     <li> A {@link PlayerContext} object for each player. The PlayerContext aggregates all the information relative to
 *     a specific player </li>
 *     <li> The market, see {@link Market}).</li>
 *     <li> The development cards table, see {@link DevelopmentCardsTable} </li>
 *     <li> The faith path, see {@link FaithPath} </li>
 * </ul>
 * <p>
 * The game context also offers some utility methods that aggregate together information obtained by multiple
 * subcomponents. For example the method {@link GameContext#getDevelopmentCardsPlayerCanBuy(Player)} will return the
 * development cards from the DevelopmentCardsTable that the player has enough resources to buy (also considering the
 * possible discounts that may apply, see {@link it.polimi.ingsw.server.model.gameitems.DevelopmentCardCostDiscount})
 */
public class GameContext {

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
	 * @param market Market, see {@link Market}
	 * @param developmentCardsTable the table with the development cards the players can buy, see
	 * 	{@link DevelopmentCardsTable}
	 * @param faithPath the faith path, see {@link FaithPath}
	 * @param playersOrder An ordered list of players that specifies the player turns order
	 * @param playerContexts a map that associates every player with his player context. See {@link PlayerContext}
	 */
	protected GameContext(
			Market market,
			DevelopmentCardsTable developmentCardsTable,
			FaithPath faithPath,
			List<Player> playersOrder,
			Map<Player,PlayerContext> playerContexts
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
	 * @return returns the playerContext for the specified player, see {@link PlayerContext}
	 */
	public PlayerContext getPlayerContext(Player player) {
		return playerContexts.get(player);
	}

	/**
	 * @return returns the Market, see {@link Market}
	 */
	public Market getMarket() {
		return market;
	}

	/**
	 * Returns the DevelopmentCardTable, a table with the development cards the players can buy
	 * @return returns the DevelopmentCardTable, see {@link DevelopmentCardsTable}
	 */
	public DevelopmentCardsTable getDevelopmentCardsTable() {
		return developmentCardsTable;
	}

	/**
	 * @return returns the FaithPath, see {@link FaithPath}
	 */
	public FaithPath getFaithPath() {
		return faithPath;
	}

	public Player getActivePlayer() {
		return activePlayer;
	}

	public Player startNextPlayerTurn(){
		activePlayer = getPlayersTurnOrder().get(
			(getPlayersTurnOrder().indexOf(activePlayer) + 1) % getPlayersTurnOrder().size()
		);
		return activePlayer;
	}
	//TODO add JavaDoc e Test

}
