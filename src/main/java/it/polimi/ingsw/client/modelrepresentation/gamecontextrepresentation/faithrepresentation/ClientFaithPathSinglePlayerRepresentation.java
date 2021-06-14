package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;

import java.util.List;
import java.util.Map;

public class ClientFaithPathSinglePlayerRepresentation extends ClientFaithPathRepresentation {

    /**
     * The position in the Faith Track of Lorenzo Magnifico, represented by a Black Cross token
     */
    private int blackCrossFaithPosition;

    public ClientFaithPathSinglePlayerRepresentation(
        @JsonProperty("faithPathLength") int faithPathLength,
        @JsonProperty("vaticanReportSections") List<ClientVaticanReportSectionRepresentation> vaticanReportSections,
        @JsonProperty("victoryPointsByPosition") int[] victoryPointsByPosition,
        @JsonProperty("faithPositions") Map<Player, Integer> faithPositions,
        @JsonProperty("popeFavorCards") Map<Player, List<PopeFavorCardState>> popeFavorCards,
        @JsonProperty("victoryPoints") Map<Player, Integer> victoryPoints,
        @JsonProperty("blackCrossFaithPosition") int blackCrossFaithPosition
    ) {
        super(faithPathLength, vaticanReportSections, victoryPointsByPosition, faithPositions, popeFavorCards, victoryPoints);
        this.blackCrossFaithPosition = blackCrossFaithPosition;
    }

    public int getBlackCrossFaithPosition() {
        return blackCrossFaithPosition;
    }

    public void setBlackCrossFaithPosition(int blackCrossFaithPosition) {
        this.blackCrossFaithPosition = blackCrossFaithPosition;
        notifyViews();
    }
}
