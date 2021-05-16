package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import it.polimi.ingsw.server.model.gamecontext.faith.VaticanReportSection;

import java.util.List;
import java.util.Map;

public class FaithPathSinglePlayerRepresentation extends FaithPathRepresentation {

    /**
     * The position in the Faith Track of Lorenzo Magnifico, represented by a Black Cross token
     */
    private int blackCrossFaithPosition;

    public FaithPathSinglePlayerRepresentation(
        int faithPathLength,
        List<VaticanReportSectionRepresentation> vaticanReportSections,
        int[] victoryPointsByPosition,
        Map<PlayerRepresentation, Integer> faithPositions,
        Map<PlayerRepresentation, List<PopeFavorCardState>> popeFavorCards,
        Map<PlayerRepresentation, Integer> victoryPoints,
        int blackCrossFaithPosition
    ) {
        super(faithPathLength, vaticanReportSections, victoryPointsByPosition, faithPositions, popeFavorCards, victoryPoints);
        this.blackCrossFaithPosition = blackCrossFaithPosition;
    }

    public int getBlackCrossFaithPosition() {
        return blackCrossFaithPosition;
    }

    public void setBlackCrossFaithPosition(int blackCrossFaithPosition) {
        this.blackCrossFaithPosition = blackCrossFaithPosition;
    }
}
