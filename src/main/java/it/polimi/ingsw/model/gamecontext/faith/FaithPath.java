package it.polimi.ingsw.model.gamecontext.faith;

import it.polimi.ingsw.model.Player;

import java.util.List;
import java.util.Map;

public class FaithPath {

	public FaithPath(Map<Player, Integer> initialFaithPosition) {

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
