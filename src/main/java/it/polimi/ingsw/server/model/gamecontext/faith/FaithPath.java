package it.polimi.ingsw.server.model.gamecontext.faith;

import it.polimi.ingsw.server.model.Player;

import java.util.*;

public class FaithPath {

	protected int faithPathLength;
	protected final List<VaticanReportSection> vaticanReportSections;
	protected int[] victoryPointsByPosition;
	protected Set<Player> players;
	protected Map<Player,Integer> faithPositions = new HashMap<>();
	protected Map<Player, List<PopeFavorCardState>> popeFavorCards = new HashMap<>();
	protected Map<Player,Integer> victoryPoints = new HashMap<>();

	public FaithPath(int faithPathLength, List<VaticanReportSection> vaticanSections,
					 int[] victoryPointsByPosition, Set<Player> players) throws IllegalArgumentException {
		if (faithPathLength != victoryPointsByPosition.length || players == null || players.isEmpty())
			throw new IllegalArgumentException();
		this.faithPathLength = faithPathLength;
		this.vaticanReportSections = vaticanSections;
		this.victoryPointsByPosition = victoryPointsByPosition;
		this.players = players;
		for (Player player : players) {
			faithPositions.put(player, 0);
			List<PopeFavorCardState> cardList = new ArrayList<>();
			for (int num=0; vaticanSections!=null && num<vaticanSections.size(); num++) {
				cardList.add(num, PopeFavorCardState.HIDDEN);
			}
			popeFavorCards.put(player,cardList);
		}
	}

	public int getPlayerFaithPosition(Player player) {
		return faithPositions.get(player);
	}

	public Map<Player, Integer> getFaithPosition() {
		return faithPositions;
	}

	public List<PopeFavorCardState> getPlayerPopeFavorCardsState(Player player) {
		return popeFavorCards.get(player);
	}

	public Map<Player, List<PopeFavorCardState>> getPopeFavorCardsState() {
		return popeFavorCards;
	}

	public Map<Player,Integer> getVictoryPoints () {
		for(Player player : players)
			victoryPoints.put(player,getPlayerVictoryPoints(player));
		return victoryPoints;
	}

	public int getPlayerVictoryPoints(Player player) {
		int victoryPoints = 0;
		for(int numSection = 0; numSection < vaticanReportSections.size(); numSection++ ) {
			if (popeFavorCards.get(player).get(numSection) == PopeFavorCardState.ACTIVE)
				victoryPoints += vaticanReportSections.get(numSection).getSectionVictoryPoints();
		}
		victoryPoints += victoryPointsByPosition[getPlayerFaithPosition(player)];
		return victoryPoints;
	}

	public boolean lastPositionHasBeenReached() {
		return faithPositions.values().stream().anyMatch(x -> (x >= faithPathLength - 1));
	}

	public FaithPathEvent move(Player player, int steps) {
		FaithPathEvent faithPathEvent;
		faithPositions.put(player, Math.min(faithPositions.get(player) + steps, faithPathLength - 1));
		boolean vaticanMeeting = false;
		int numSection = 0;
		for (VaticanReportSection section : vaticanReportSections) {
			if (getPlayerFaithPosition(player) >= section.getPopeSpacePos() &&
					popeFavorCards.get(player).get(numSection) == PopeFavorCardState.HIDDEN) {
				vaticanMeeting = true;
				popeFavorCards.get(player).set(numSection, PopeFavorCardState.ACTIVE);
				for(Player p : players)
					if (p != player) {
						if (getPlayerFaithPosition(p) >= section.getSectionInitialPos())
							popeFavorCards.get(p).set(numSection, PopeFavorCardState.ACTIVE);
						else
							popeFavorCards.get(p).set(numSection, PopeFavorCardState.DISCARDED);
					}
			}
			numSection++;
		}
		if(vaticanMeeting) {
			if (lastPositionHasBeenReached())
				return new FaithPathEvent(true, true);
			return new FaithPathEvent(false, true);
		}
		if (lastPositionHasBeenReached())
			faithPathEvent = new FaithPathEvent(true, false);
		else
			faithPathEvent = new FaithPathEvent(false, false);
		return faithPathEvent;
	}
}