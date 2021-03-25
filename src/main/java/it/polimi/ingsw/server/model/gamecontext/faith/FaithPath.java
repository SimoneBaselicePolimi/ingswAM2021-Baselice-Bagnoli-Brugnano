package it.polimi.ingsw.server.model.gamecontext.faith;

import it.polimi.ingsw.server.model.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FaithPath {

	public FaithPath(
			int faithPathLength,
			List<VaticanReportSection> vaticanSections,
			int[] victoryPointsByPosition,
            Set<Player> players
	) { }

	public int getPlayerFaithPosition(Player player) {
		return 0;
	}

	public Map<Player, Integer> getFaithPosition() {
		return null;
	}

	public List<PopeFavorCardState> getPlayerPopeFavorCardsState(Player player) {
		return null;
	}

	public Map<Player, List<PopeFavorCardState>> getPopeFavorCardsState() {
		return null;
	}

	public boolean lastPositionHasBeenReached() {
		return false;
	}

	public int getPlayerVictoryPoints(Player player) {
		return 0;
	}

	public FaithPathEvent move(Player player, int steps) {
		return null;
	}

}
