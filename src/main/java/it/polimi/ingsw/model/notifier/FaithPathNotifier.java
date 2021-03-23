package it.polimi.ingsw.model.notifier;

import it.polimi.ingsw.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.model.gamecontext.faith.FaithPathEvent;
import it.polimi.ingsw.model.Player;

public class FaithPathNotifier extends FaithPath implements Notifier {

	public Option<FaithUpdate> getUpdate() {
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
