package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathEvent;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.VaticanReportSection;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;

import java.util.List;
import java.util.Set;

public class FaithPathNotifier extends FaithPath implements Notifier {

	public FaithPathNotifier(int faithPathLength, List<VaticanReportSection> vaticanSections, int[] victoryPointsByPosition, Set<Player> players, GameHistory gameHistory) throws IllegalArgumentException {
		super(faithPathLength, vaticanSections, victoryPointsByPosition, players, gameHistory);
	}

	public Set<ServerGameUpdate> getUpdates() {
		return null;
	}

	public FaithPathEvent move(Player player, int steps) {
		return null;
	}

}
