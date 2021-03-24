package it.polimi.ingsw.server.model.gamecontext.faith;

import it.polimi.ingsw.server.model.Player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FaithPath {

	public FaithPath(
		int faithPathLength,
		List<VaticanReportSection> vaticanSections,
		int[] victoryPointsByPosition,
		Map<Player, Integer> initialFaithPosition
	) {

	}

	public FaithPath(
			int faithPathLength,
			List<VaticanReportSection> vaticanSections,
			int[] victoryPointsByPosition,
            List<Player> players
	) {
		this(
			faithPathLength,
			vaticanSections,
			victoryPointsByPosition,
			players.stream().collect(Collectors.toMap(p -> p, p -> 0))  //set position of every player to 0
		);
	}

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

	public FaithPathEvent move(Player player, int steps) {
		return null;
	}

}
