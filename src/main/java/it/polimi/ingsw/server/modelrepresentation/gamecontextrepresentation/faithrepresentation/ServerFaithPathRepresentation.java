package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;

import java.util.List;
import java.util.Map;

public class ServerFaithPathRepresentation extends ServerRepresentation {

    /**
     * Length of the Faith Track
     */
    public final int faithPathLength;

    /**
     * List of spaces in which a Vatican Report occurs
     */
    public final List<ServerVaticanReportSectionRepresentation> vaticanReportSections;

    /**
     * Array of victory points given at the end of the Game to the Player based on his position in the Faith Track
     */
    public final int[] victoryPointsByPosition;

    /**
     * Position of each Player in the Faith Track
     */
    @SerializeAsMapWithIdKey
    public final Map<Player, Integer> faithPositions;

    /**
     * State of the Pope's Favor cards of each Player
     */
    @SerializeAsMapWithIdKey
    public final Map<Player, List<PopeFavorCardState>> popeFavorCards;

    /**
     * Victory points scored by each Player
     */
    @SerializeAsMapWithIdKey
    public final Map<Player, Integer> victoryPoints;

    public ServerFaithPathRepresentation(
        @JsonProperty("faithPathLength") int faithPathLength,
        @JsonProperty("vaticanReportSections") List<ServerVaticanReportSectionRepresentation> vaticanReportSections,
        @JsonProperty("victoryPointsByPosition") int[] victoryPointsByPosition,
        @JsonProperty("faithPositions") Map<Player, Integer> faithPositions,
        @JsonProperty("popeFavorCards") Map<Player, List<PopeFavorCardState>> popeFavorCards,
        @JsonProperty("victoryPoints") Map<Player, Integer> victoryPoints
    ) {
        this.faithPathLength = faithPathLength;
        this.vaticanReportSections = vaticanReportSections;
        this.victoryPointsByPosition = victoryPointsByPosition;
        this.faithPositions = faithPositions;
        this.popeFavorCards = popeFavorCards;
        this.victoryPoints = victoryPoints;
    }
}
