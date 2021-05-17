package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

import java.util.List;
import java.util.Map;

public class ServerFaithPathSinglePlayerRepresentation extends ServerFaithPathRepresentation {

    /**
     * The position in the Faith Track of Lorenzo Magnifico, represented by a Black Cross token
     */
    public final int blackCrossFaithPosition;

    public ServerFaithPathSinglePlayerRepresentation(
        int faithPathLength,
        List<ServerVaticanReportSectionRepresentation> vaticanReportSections,
        int[] victoryPointsByPosition,
        Map<ServerPlayerRepresentation, Integer> faithPositions,
        Map<ServerPlayerRepresentation, List<PopeFavorCardState>> popeFavorCards,
        Map<ServerPlayerRepresentation, Integer> victoryPoints,
        int blackCrossFaithPosition
    ) {
        super(faithPathLength, vaticanReportSections, victoryPointsByPosition, faithPositions, popeFavorCards, victoryPoints);
        this.blackCrossFaithPosition = blackCrossFaithPosition;
    }
}
