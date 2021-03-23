package it.polimi.ingsw.model.notifier;

import it.polimi.ingsw.model.gamecontext.faith.FaithPathSinglePlayer;
import it.polimi.ingsw.model.gamecontext.faith.FaithPathEvent;
import it.polimi.ingsw.model.Player;

public class FaithPathSinglePlayerNotifier extends FaithPathSinglePlayer implements Notifier {

	public Option<FaithSinglePlayerUpdate> getUpdate() {
		return null;
	}

	public FaithPathEvent moveBlackCross(int steps) {
		return null;
	}

	public FaithPathEvent move(Player player, int steps) {
		return null;
	}


	/**
	 * @see Notifier#getUpdate()
	 */
	public Optional<GameUpdate> getUpdate() {
		return null;
	}

}
