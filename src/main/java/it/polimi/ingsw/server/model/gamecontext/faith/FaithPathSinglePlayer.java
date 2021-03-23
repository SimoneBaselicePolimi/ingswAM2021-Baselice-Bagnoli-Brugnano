package it.polimi.ingsw.server.model.gamecontext.faith;

import it.polimi.ingsw.server.model.Player;

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
