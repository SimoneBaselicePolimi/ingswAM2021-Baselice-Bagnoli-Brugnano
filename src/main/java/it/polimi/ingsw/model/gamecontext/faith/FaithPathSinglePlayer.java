package it.polimi.ingsw.model.gamecontext.faith;

import it.polimi.ingsw.model.Player;

import java.util.Map;

public class FaithPathSinglePlayer extends FaithPath {

	public FaithPathSinglePlayer(Map<Player, Integer> initialFaithPosition) {
		super(initialFaithPosition);
	}

	public FaithPathEvent moveBlackCross(int steps) {
		return null;
	}

	public int getBlackCrossFaithPosition() {
		return 0;
	}

}
