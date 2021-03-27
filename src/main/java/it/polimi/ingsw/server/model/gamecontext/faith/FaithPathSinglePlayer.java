package it.polimi.ingsw.server.model.gamecontext.faith;

import it.polimi.ingsw.server.model.Player;

import java.util.List;
import java.util.Set;

public class FaithPathSinglePlayer extends FaithPath {

    private int blackCrossFaithPosition;

	public FaithPathSinglePlayer(int faithPathLength, List<VaticanReportSection> vaticanSections,
                                 int[] victoryPointsByPosition, Set<Player> player) {
		super(faithPathLength, vaticanSections, victoryPointsByPosition, player);
	}

	public int getBlackCrossFaithPosition() {
		return blackCrossFaithPosition;
	}

	public FaithPathEvent moveBlackCross(int steps) {
        FaithPathEvent faithPathEvent;
        blackCrossFaithPosition = Math.min(blackCrossFaithPosition + steps, faithPathLength - 1);
        if(blackCrossFaithPosition == faithPathLength - 1)
        	return new FaithPathEvent(true, false);
		boolean vaticanMeeting = false;
		int numSection = 0;
		for (VaticanReportSection section : vaticanReportSections) {
			if (blackCrossFaithPosition >= section.getPopeSpacePos()) {
				vaticanMeeting = true;
				for(Player p : players)
					if (getPlayerFaithPosition(p) >= section.getSectionInitialPos())
						popeFavorCards.get(p).set(numSection, PopeFavorCardState.ACTIVE);
					else
						popeFavorCards.get(p).set(numSection, PopeFavorCardState.DISCARDED);
			}
			numSection++;
		}
		if(vaticanMeeting)
			return new FaithPathEvent(false, true);
		return new FaithPathEvent(false, false);
	}
}
