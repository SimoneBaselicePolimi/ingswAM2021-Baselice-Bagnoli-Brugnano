package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathSinglePlayerImp;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathEvent;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.VaticanReportSection;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;

import java.util.List;
import java.util.Set;

public class FaithPathSinglePlayerNotifier extends FaithPathSinglePlayerImp implements Notifier {

	public FaithPathSinglePlayerNotifier(int faithPathLength, List<VaticanReportSection> vaticanSections, int[] victoryPointsByPosition, Player player, GameHistory gameHistory) {
		super(faithPathLength, vaticanSections, victoryPointsByPosition, player, gameHistory);
	}

	public Set<ServerGameUpdate> getUpdates() {
		return null;
	}

	public FaithPathEvent moveBlackCross(int steps) {
		return null;
	}

	public FaithPathEvent move(Player player, int steps) {
		return null;
	}

}
