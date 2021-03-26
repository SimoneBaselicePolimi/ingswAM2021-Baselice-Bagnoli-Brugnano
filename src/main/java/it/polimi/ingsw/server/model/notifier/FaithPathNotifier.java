package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathEvent;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.notifier.gameupdate.FaithUpdate;

import java.util.Map;
import java.util.Optional;

public class FaithPathNotifier extends FaithPath implements Notifier<FaithUpdate> {

	public FaithPathNotifier(Map<Player, Integer> initialFaithPosition) {
		super(initialFaithPosition);
	}

	public Optional<FaithUpdate> getUpdate() {
		return null;
	}

	public FaithPathEvent move(Player player, int steps) {
		return null;
	}

}
