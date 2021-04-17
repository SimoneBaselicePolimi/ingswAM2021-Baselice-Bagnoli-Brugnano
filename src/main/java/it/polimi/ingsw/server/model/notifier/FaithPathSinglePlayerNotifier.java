package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathSinglePlayer;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathEvent;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.VaticanReportSection;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;

import java.util.List;
import java.util.Set;

public class FaithPathSinglePlayerNotifier extends FaithPathSinglePlayer implements Notifier {

	public FaithPathSinglePlayerNotifier(int faithPathLength, List<VaticanReportSection> vaticanSections, int[] victoryPointsByPosition, Player player) {
		super(faithPathLength, vaticanSections, victoryPointsByPosition, player);
	}

	public Set<GameUpdate> getUpdates() {
		return null;
	}

	public FaithPathEvent moveBlackCross(int steps) {
		return null;
	}

	public FaithPathEvent move(Player player, int steps) {
		return null;
	}

}
