package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import it.polimi.ingsw.server.model.gamecontext.faith.VaticanReportSection;

import java.util.List;
import java.util.Map;

public class FaithPathRepresentation extends Representation{

    /**
     * Length of the Faith Track
     */
    protected final int faithPathLength;

    /**
     * List of spaces in which a Vatican Report occurs
     */
    protected final List<VaticanReportSectionRepresentation> vaticanReportSections;

    /**
     * Array of victory points given at the end of the Game to the Player based on his position in the Faith Track
     */
    protected final int[] victoryPointsByPosition;

    /**
     * Position of each Player in the Faith Track
     */
    protected Map<PlayerRepresentation,Integer> faithPositions;

    /**
     * State of the Pope's Favor cards of each Player
     */
    protected Map<PlayerRepresentation, List<PopeFavorCardState>> popeFavorCards;

    /**
     * Victory points scored by each Player
     */
    protected Map<PlayerRepresentation,Integer> victoryPoints;

    public FaithPathRepresentation(
        int faithPathLength,
        List<VaticanReportSectionRepresentation> vaticanReportSections,
        int[] victoryPointsByPosition,
        Map<PlayerRepresentation, Integer> faithPositions,
        Map<PlayerRepresentation, List<PopeFavorCardState>> popeFavorCards,
        Map<PlayerRepresentation, Integer> victoryPoints
    ) {
        this.faithPathLength = faithPathLength;
        this.vaticanReportSections = vaticanReportSections;
        this.victoryPointsByPosition = victoryPointsByPosition;
        this.faithPositions = faithPositions;
        this.popeFavorCards = popeFavorCards;
        this.victoryPoints = victoryPoints;
    }

    public int getFaithPathLength() {
        return faithPathLength;
    }

    public List<VaticanReportSectionRepresentation> getVaticanReportSections() {
        return vaticanReportSections;
    }

    public int[] getVictoryPointsByPosition() {
        return victoryPointsByPosition;
    }

    public Map<PlayerRepresentation, Integer> getFaithPositions() {
        return faithPositions;
    }

    public Map<PlayerRepresentation, List<PopeFavorCardState>> getPopeFavorCards() {
        return popeFavorCards;
    }

    public Map<PlayerRepresentation, Integer> getVictoryPoints() {
        return victoryPoints;
    }

    public void setFaithPositions(Map<PlayerRepresentation, Integer> faithPositions) {
        this.faithPositions = faithPositions;
    }

    public void setPopeFavorCards(Map<PlayerRepresentation, List<PopeFavorCardState>> popeFavorCards) {
        this.popeFavorCards = popeFavorCards;
    }

    public void setVictoryPoints(Map<PlayerRepresentation, Integer> victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
