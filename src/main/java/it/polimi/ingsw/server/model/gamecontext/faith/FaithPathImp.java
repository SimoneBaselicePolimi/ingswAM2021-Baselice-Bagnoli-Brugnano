package it.polimi.ingsw.server.model.gamecontext.faith;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamehistory.*;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation.ServerFaithPathRepresentation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents the Faith Track, in which every Player moves a Faith Marker.
 * When a Player reaches a Pope space, a Vatican Report occurs and all the Players change the state of their
 * Pope’s Favor cards. At the end of the Game, victory points due to Pope’s Favor cards and position in the Track
 * are added together.
 */
public class FaithPathImp implements FaithPath {
	/**
	 * Length of the Faith Track
	 */
	protected int faithPathLength;

	/**
	 * List of spaces in which a Vatican Report occurs
	 */
	protected final List<VaticanReportSection> vaticanReportSections;

	/**
	 * Array of victory points given at the end of the Game to the Player based on his position in the Faith Track
	 */
	protected int[] victoryPointsByPosition;

	/**
	 * Position of each Player in the Faith Track
	 */
	protected Map<Player,Integer> faithPositions = new HashMap<>();

	/**
	 * State of the Pope's Favor cards of each Player
	 */
	protected Map<Player, List<PopeFavorCardState>> popeFavorCards = new HashMap<>();

	/**
	 * Victory points scored by each Player
	 */
	protected Map<Player,Integer> victoryPoints = new HashMap<>();

	/**
	 * @see GameHistoryImp
	 */
	protected GameHistory gameHistory;

	/**
	 * Faith Path constructor.
	 * Initialize each Player's position in the Faith Track and the state of the Pope's Favor cards.
	 * @param faithPathLength length of the Faith Track
	 * @param vaticanSections spaces in which a Vatican Report can occur
	 * @param victoryPointsByPosition victory points given by each space in the Faith Track
	 * @param players players in the Game
	 * @param gameHistory see {@link GameHistoryImp}. Events triggered by a move action are automatically logged in the
	 *                    Game History
	 * @throws IllegalArgumentException if there are no Players or if the victory points passed as parameter
	 * are not as many as the spaces in the Faith Track
	 */
	public FaithPathImp(
		int faithPathLength,
		List<VaticanReportSection> vaticanSections,
		int[] victoryPointsByPosition,
		Set<Player> players,
		GameHistory gameHistory
	) throws IllegalArgumentException {
		if (faithPathLength != victoryPointsByPosition.length || players == null || players.isEmpty())
			throw new IllegalArgumentException();
		this.faithPathLength = faithPathLength;
		this.vaticanReportSections = vaticanSections;
		this.victoryPointsByPosition = victoryPointsByPosition;
		this.gameHistory = gameHistory;

		for (Player player : players) {
			faithPositions.put(player, 0);
			List<PopeFavorCardState> cardList = new ArrayList<>();
			for (int num=0; vaticanSections!=null && num<vaticanSections.size(); num++) {
				cardList.add(num, PopeFavorCardState.HIDDEN);
			}
			popeFavorCards.put(player,cardList);
		}
	}

	/**
	 * Returns the position of a specific Player in the Faith Track.
	 * @param player player to get the position of
	 * @return position of the Player in the Faith Track
	 */
	@Override
	public int getPlayerFaithPosition(Player player) {
		return faithPositions.get(player);
	}

	/**
	 * Returns the position of each Player in the Faith Track.
	 * @return positions of each Player in the Faith Track
	 */
	@Override
	public Map<Player, Integer> getFaithPosition() {
		return faithPositions;
	}

	/**
	 * Returns the states of the Pope's Favor cards of a specific Player.
	 * @param player player to get the Pope's Favor cards of
	 * @return states of the Pope's Favor cards of the Player
	 */
	@Override
	public List<PopeFavorCardState> getPlayerPopeFavorCardsState(Player player) {
		return popeFavorCards.get(player);
	}

	/**
	 * Returns the states of the Pope's Favor cards of each Player.
	 * @return states of the Pope's Favor cards of each Player
	 */
	@Override
	public Map<Player, List<PopeFavorCardState>> getPopeFavorCardsState() {
		return popeFavorCards;
	}

	/**
	 * Returns the victory points of a specific Player based on his active Pope's Favor cards and
	 * his position in the Faith Track.
	 * @param player player to get the victory points of
	 * @return number of victory points scored by the Player
	 */
	@Override
	public int getPlayerVictoryPoints(Player player) {
		int victoryPoints = 0;
		for(int numSection = 0; numSection < vaticanReportSections.size(); numSection++ ) {
			if (popeFavorCards.get(player).get(numSection) == PopeFavorCardState.ACTIVE)
				victoryPoints += vaticanReportSections.get(numSection).getSectionVictoryPoints();
		}
		victoryPoints += victoryPointsByPosition[getPlayerFaithPosition(player)];
		return victoryPoints;
	}

	/**
	 * Returns the victory points scored by each Player.
	 * @return number of victory points scored by each Player
	 */
	@Override
	public Map<Player,Integer> getVictoryPoints() {
		for(Player player : victoryPoints.keySet())
			victoryPoints.put(player,getPlayerVictoryPoints(player));
		return victoryPoints;
	}

	/**
	 * Returns true if the last position of the Faith Track has been reached, triggering the end of the Game.
	 * @return true if at least one Player has reached the end of the Faith Track, false if no Player has reached it
	 */
	@Override
	public boolean lastPositionHasBeenReached() {
		return faithPositions.values().stream().anyMatch(x -> (x == faithPathLength - 1));
	}

	/**
	 * This method represent the action of a Player moving in the Faith Track for a specific number of steps forward.
	 * This action can trigger a Vatican Report if a Pope space is reached by the Player. In this case, each Player
	 * has to change the state of his Pope's Favor cards based on their positions in the Track.
	 * @param player Player moving in the Faith Track
	 * @param steps number of steps a Player has to move forward in the Track
	 * @return a Faith Path Event, which states if a Vatican Report happened or if the end of the Faith Track
	 * is reached by the Player after moving
	 */
	@Override
	public FaithPathEvent move(Player player, int steps) {
		faithPositions.put(player, Math.min(faithPositions.get(player) + steps, faithPathLength - 1));

		gameHistory.addAction(
			new FaithPathMoveAction(player, steps)
		);

		boolean vaticanReport = false;
		int numSection = 0;
		for (VaticanReportSection section : vaticanReportSections) {
			if (getPlayerFaithPosition(player) >= section.getPopeSpacePos() &&
					popeFavorCards.get(player).get(numSection) == PopeFavorCardState.HIDDEN) {
				vaticanReport = true;

				gameHistory.addAction(new FaithPathVaticanReportAction());

				for(Player p : popeFavorCards.keySet())
					turnPopeFavorCard(p, section, numSection);
			}
			numSection++;
		}
		boolean lastPositionReached = lastPositionHasBeenReached();
		if (lastPositionReached)
			gameHistory.addAction(new FaithPathLastPositionReachedAction(player));
		return new FaithPathEvent(lastPositionReached, vaticanReport);
	}

	/**
	 * Applies the changes of state to the Pope's Favor cards of a specific Player when a Vatican Report occurred.
	 * The Pope's Favor card is activated if the Player's position is within (or beyond) the Vatican Report section
	 * where this Vatican Report occurred, otherwise it is discarded.
	 * @param player player who owns the Pope's Favor card to change state of
	 * @param section characteristics of the Vatican Report section in which this Vatican Report occurred
	 * @param numSection number of this Vatican Report section
	 */
	protected void turnPopeFavorCard(Player player, VaticanReportSection section, int numSection) {
		if (getPlayerFaithPosition(player) >= section.getSectionInitialPos())
			popeFavorCards.get(player).set(numSection, PopeFavorCardState.ACTIVE);
		else
			popeFavorCards.get(player).set(numSection, PopeFavorCardState.DISCARDED);
	}

	@Override
	public ServerFaithPathRepresentation getServerRepresentation() {
		return new ServerFaithPathRepresentation(
			faithPathLength,
			vaticanReportSections.stream().map(VaticanReportSection::getServerRepresentation).collect(Collectors.toList()),
			victoryPointsByPosition,
			faithPositions.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
			popeFavorCards.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
			victoryPoints.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
			);
	}

	@Override
	public ServerFaithPathRepresentation getServerRepresentationForPlayer(Player player) {
		return getServerRepresentation();
	}
}
