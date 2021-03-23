package it.polimi.ingsw.model.notifier;

import it.polimi.ingsw.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.model.gamecontext.faith.FaithPathEvent;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.notifier.gameupdate.FaithUpdate;
import it.polimi.ingsw.model.notifier.gameupdate.GameUpdate;

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
