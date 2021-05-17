package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;

import java.util.List;
import java.util.Map;

public class ClientFaithPathSinglePlayerRepresentation extends ClientFaithPathRepresentation {

    /**
     * The position in the Faith Track of Lorenzo Magnifico, represented by a Black Cross token
     */
    private int blackCrossFaithPosition;

    public ClientFaithPathSinglePlayerRepresentation(
        int faithPathLength,
        List<ClientVaticanReportSectionRepresentation> vaticanReportSections,
        int[] victoryPointsByPosition,
        Map<ClientPlayerRepresentation, Integer> faithPositions,
        Map<ClientPlayerRepresentation, List<PopeFavorCardState>> popeFavorCards,
        Map<ClientPlayerRepresentation, Integer> victoryPoints,
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
