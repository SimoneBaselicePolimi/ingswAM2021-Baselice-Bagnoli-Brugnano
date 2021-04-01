package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathEvent;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.VaticanReportSection;
import it.polimi.ingsw.server.model.notifier.gameupdate.FaithUpdate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class FaithPathNotifier extends FaithPath implements Notifier<FaithUpdate> {

	public FaithPathNotifier(int faithPathLength, List<VaticanReportSection> vaticanSections, int[] victoryPointsByPosition, Set<Player> players) throws IllegalArgumentException {
		super(faithPathLength, vaticanSections, victoryPointsByPosition, players);
	}

	public Optional<FaithUpdate> getUpdate() {
		return null;
	}

	public FaithPathEvent move(Player player, int steps) {
		return null;
	}

}
