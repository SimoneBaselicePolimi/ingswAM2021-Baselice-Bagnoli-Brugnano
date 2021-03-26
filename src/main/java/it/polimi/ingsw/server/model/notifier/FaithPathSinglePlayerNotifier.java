package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathSinglePlayer;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathEvent;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.notifier.gameupdate.FaithSinglePlayerUpdate;

import java.util.Map;
import java.util.Optional;

public class FaithPathSinglePlayerNotifier extends FaithPathSinglePlayer implements Notifier<FaithSinglePlayerUpdate> {

	public FaithPathSinglePlayerNotifier(Map<Player, Integer> initialFaithPosition) {
		super(initialFaithPosition);
	}

	public Optional<FaithSinglePlayerUpdate> getUpdate() {
		return null;
	}

	public FaithPathEvent moveBlackCross(int steps) {
		return null;
	}

	public FaithPathEvent move(Player player, int steps) {
		return null;
	}

}
