package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;

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
    public final Map<ServerPlayerRepresentation,Integer> faithPositions;

    /**
     * State of the Pope's Favor cards of each Player
     */
    public final Map<ServerPlayerRepresentation, List<PopeFavorCardState>> popeFavorCards;

    /**
     * Victory points scored by each Player
     */
    public final Map<ServerPlayerRepresentation,Integer> victoryPoints;

    public ServerFaithPathRepresentation(
        int faithPathLength,
        List<ServerVaticanReportSectionRepresentation> vaticanReportSections,
        int[] victoryPointsByPosition,
        Map<ServerPlayerRepresentation, Integer> faithPositions,
        Map<ServerPlayerRepresentation, List<PopeFavorCardState>> popeFavorCards,
        Map<ServerPlayerRepresentation, Integer> victoryPoints
    ) {
        this.faithPathLength = faithPathLength;
        this.vaticanReportSections = vaticanReportSections;
        this.victoryPointsByPosition = victoryPointsByPosition;
        this.faithPositions = faithPositions;
        this.popeFavorCards = popeFavorCards;
        this.victoryPoints = victoryPoints;
    }
}
